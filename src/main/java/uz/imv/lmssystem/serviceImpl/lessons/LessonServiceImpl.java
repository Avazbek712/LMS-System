package uz.imv.lmssystem.serviceImpl.lessons;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.entity.Lesson;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.Schedule;
import uz.imv.lmssystem.exceptions.ScheduleConflictException;
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

    private List<Lesson> generateLessonsForPeriod(Group group, LocalDate startDate, LocalDate endDate) {
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

    private void checkForConflicts(Lesson lessonToCheck) throws ScheduleConflictException {

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