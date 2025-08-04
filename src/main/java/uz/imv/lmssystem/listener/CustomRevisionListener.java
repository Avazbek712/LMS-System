package uz.imv.lmssystem.listener;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.imv.lmssystem.entity.CustomRevisionEntity;
import uz.imv.lmssystem.entity.User;

/**
 * Created by Avazbek on 30/07/25 15:32
 */
public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return;
        }

        User user = (User) authentication.getPrincipal();
        customRevisionEntity.setUserId(user.getId());
    }
}
