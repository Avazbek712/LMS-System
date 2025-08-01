package uz.imv.lmssystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.audit.AuditorAwareImpl;

/**
 * Created by Avazbek on 30/07/25 15:28
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

    @Bean
    public AuditorAware<User> auditorAware() {
        return new AuditorAwareImpl();
    }
}
