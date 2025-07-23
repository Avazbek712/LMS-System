package uz.imv.lmssystem.entity.template;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

/**
 * Created by Avazbek on 22/07/25 10:36
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted;
}
