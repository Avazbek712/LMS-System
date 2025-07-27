package uz.imv.lmssystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.repository.LessonRepository;

import java.time.LocalDate;

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
                .findLatestLessonDateByGroupId(group.getId()).orElse(group.getStartDate().minusDays(2));

        LocalDate nexDateToGenerate = lastGeneratedDate.plusDays(2);

        if (nexDateToGenerate.isAfter(generationCutoffDate))
            return;

        // если это послдение уроки то останавливается в последний день группы
        LocalDate generationEndDate = group.getEndDate().isBefore(generationCutoffDate)
                ? group.getEndDate()
                : generationCutoffDate;
    }
}