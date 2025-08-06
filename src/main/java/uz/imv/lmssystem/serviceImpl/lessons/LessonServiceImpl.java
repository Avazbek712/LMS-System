package uz.imv.lmssystem.serviceImpl.lessons;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.dto.LessonDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.entity.Lesson;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.Schedule;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.exceptions.ScheduleConflictException;
import uz.imv.lmssystem.mapper.LessonMapper;
import uz.imv.lmssystem.repository.LessonRepository;
import uz.imv.lmssystem.service.lessons.LessonService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Avazbek on 27/07/25 14:48
 */
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateAndSaveLessonsForGroup(Group group, LocalDate generationCutoffDate) {
        LocalDate lastGeneratedDate = lessonRepository
                .findLatestLessonDateByGroupId(group.getId()).orElse(group.getStartDate().minusDays(1));

        LocalDate nextDateToGenerate = lastGeneratedDate.plusDays(1);

        if (nextDateToGenerate.isAfter(generationCutoffDate)) {
            return;
        }

        LocalDate generationEndDate = group.getEndDate().isBefore(generationCutoffDate)
                ? group.getEndDate()
                : generationCutoffDate;

        if (nextDateToGenerate.isAfter(generationEndDate)) {
            return;
        }

        List<Lesson> newLessons = generateLessonsForPeriod(group, nextDateToGenerate, generationEndDate);

        if (newLessons.isEmpty()) {
            return;
        }

        for (Lesson lesson : newLessons) {
            this.checkForConflicts(lesson);
        }

        lessonRepository.saveAll(newLessons);
    }

    @Override
    @Transactional
    @Cacheable(value = "lessons_list", key = "'page:' + #page + ':size:' + #size")
    public PageableDTO lessonToTheDay(LocalDate date, int page, int size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Lesson> lessonPages = lessonRepository.findByDate(date, pageable);
        List<Lesson> lessons = lessonPages.getContent();

        if (lessons.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, List.of());
        }

        List<LessonDTO> lessonDTOs = lessons.stream().map(lessonMapper::toDTO).toList();

        return new PageableDTO(
                lessonPages.getSize(),
                lessonPages.getTotalElements(),
                lessonPages.getTotalPages(),
                !lessonPages.isLast(),
                !lessonPages.isFirst(),
                lessonDTOs
        );
    }

    @Override
    @Cacheable(value = "lessons_list", key = "'page:' + #page + ':size:' + #size")
    public PageableDTO getAll(int page, int size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Lesson> lessonsPage = lessonRepository.findAll(pageable);
        List<Lesson> lessons = lessonsPage.getContent();


        if (lessons.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, List.of());
        }

        List<LessonDTO> lessonDTOS = lessons.stream().map(lessonMapper::toDTO).toList();

        return new PageableDTO(
                lessonsPage.getSize(),
                lessonsPage.getTotalElements(),
                lessonsPage.getTotalPages(),
                !lessonsPage.isLast(),
                !lessonsPage.isFirst(),
                lessonDTOS
        );
    }

    @Override
    public LessonDTO getById(Long id) {

        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lesson id " + id + " not found"));

        return lessonMapper.toDTO(lesson);
    }


    @Override
    public List<Lesson> generateLessonsForPeriod(Group group, LocalDate startDate, LocalDate endDate) {
        List<Lesson> lessons = new ArrayList<>();

        Set<Schedule> scheduleEnums = group.getSchedule();
        LocalTime startTime = group.getLessonStartTime();
        LocalTime endTime = group.getLessonEndTime();


        if (Objects.isNull(scheduleEnums) || scheduleEnums.isEmpty() || Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return lessons;
        }


        Set<DayOfWeek> daysOfWeek = scheduleEnums.stream().map(s ->
                DayOfWeek.valueOf(s.name())).collect(Collectors.toSet());

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (daysOfWeek.contains(currentDate.getDayOfWeek())) {
                Lesson lesson = new Lesson();

                lesson.setGroup(group);
                lesson.setDate(currentDate);
                lesson.setStartTime(startTime);
                lesson.setEndTime(endTime);
                lessons.add(lesson);
            }
            currentDate = currentDate.plusDays(1);

        }
        return lessons;
    }


    @Override
    public void checkForConflicts(Lesson lessonToCheck) throws ScheduleConflictException {

        List<Lesson> conflictingLessons = lessonRepository.findConflictingLessons(
                lessonToCheck.getGroup().getRoom().getId(),
                lessonToCheck.getDate(),
                lessonToCheck.getStartTime(),
                lessonToCheck.getEndTime()
        );
        if (!conflictingLessons.isEmpty()) {
            Lesson existingLesson = conflictingLessons.get(0);
            String errorMessage = String.format(
                    "Room  '%s' is already occupied at  %s from %s till %s by group lesson '%s'.",
                    existingLesson.getGroup().getRoom().getName(),
                    existingLesson.getDate(),
                    existingLesson.getStartTime(),
                    existingLesson.getEndTime(),
                    existingLesson.getGroup().getName()
            );
            throw new ScheduleConflictException(errorMessage);
        }

        if (lessonToCheck.getGroup().getTeacher() != null) {
            List<Lesson> teacherConflicts = lessonRepository.findConflictingLessonsForTeacher(
                    lessonToCheck.getGroup().getTeacher().getId(),
                    lessonToCheck.getDate(),
                    lessonToCheck.getStartTime(),
                    lessonToCheck.getEndTime()
            );
            if (!teacherConflicts.isEmpty()) {
                String errorMessage = getErrorMessage(teacherConflicts);
                throw new ScheduleConflictException(errorMessage);
            }
        }
    }

    private static String getErrorMessage(List<Lesson> teacherConflicts) {
        Lesson existingLesson = teacherConflicts.get(0);
        User teacher = existingLesson.getGroup().getTeacher();
        return String.format(
                "Teacher CONFLICT: %s %s is teaching at %s from %s till %s in the group name '%s'.",
                teacher.getName(),
                teacher.getSurname(),
                existingLesson.getDate(),
                existingLesson.getStartTime(),
                existingLesson.getEndTime(),
                existingLesson.getGroup().getName()
        );
    }
}