package uz.imv.lmssystem.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.repository.GroupRepository;
import uz.imv.lmssystem.service.LessonService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Avazbek on 27/07/25 17:12
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LessonCreationScheduler {

    private final GroupRepository groupRepository;
    private final LessonService lessonService;

    @Scheduled(cron = "0 0 2 * * *")
    public void generateUpcomingLessons() {

        log.info("--- Запуск ежедневной задачи по генерации уроков ---");

        final LocalDate generationCutoffDate = LocalDate.now().plusWeeks(1);


        List<Group> activeGroups = groupRepository.findAllByStatusAndEndDateAfter(GroupStatus.OPEN, LocalDate.now());

        log.info("Найдено {} активных групп для обработки.", activeGroups.size());

        for (Group group : activeGroups) {
            try {
                lessonService.generateAndSaveLessonsForGroup(group, generationCutoffDate);
            } catch (Exception e) {

                log.error("Критическая ошибка при обработке группы ID {}: {}", group.getId(), e.getMessage());
            }
        }


    }


}
