package uz.imv.lmssystem.entity.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.imv.lmssystem.entity.User;

import java.util.Optional;

/**
 * Created by Avazbek on 30/07/25 15:26
 */
public class AuditorAwareImpl implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }

        return Optional.of((User) authentication.getPrincipal());
    }
}
