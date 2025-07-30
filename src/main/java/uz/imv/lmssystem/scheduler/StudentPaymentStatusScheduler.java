package uz.imv.lmssystem.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.service.StudentService;

/**
 * Created by Avazbek on 30/07/25 09:33
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StudentPaymentStatusScheduler {

    private final StudentService studentService;

    @Scheduled(cron = "0 0 1 1 * *")
    public void changePaymentStatuses() {

        try {
            int updatedCount = studentService.resetExpiredPaymentStatuses();
            log.info("--- Задача завершена. Статус оплаты обновлен для {} студентов. ---", updatedCount);
        } catch (Exception e) {
            log.error("Произошла ошибка во время обновления статусов оплаты: {}", e.getMessage(), e);
        }
    }
}

