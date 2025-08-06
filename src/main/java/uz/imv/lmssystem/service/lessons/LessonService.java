package uz.imv.lmssystem.service.lessons;

import uz.imv.lmssystem.dto.LessonDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.entity.Lesson;
import uz.imv.lmssystem.exceptions.ScheduleConflictException;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {

    void generateAndSaveLessonsForGroup(Group group, LocalDate generationCutoffDate);

    PageableDTO lessonToTheDay(LocalDate date, int page, int size);

    PageableDTO getAll(int page, int size);

    LessonDTO getById(Long id);


    List<Lesson> generateLessonsForPeriod(Group group, LocalDate startDate, LocalDate endDate);

    void checkForConflicts(Lesson lessonToCheck) throws ScheduleConflictException;
}
