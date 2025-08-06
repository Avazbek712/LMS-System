package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import uz.imv.lmssystem.listener.CustomRevisionListener;

/**
 * Created by Avazbek on 30/07/25 15:31
 */

@Entity
@Table(name = "rev_info")
@RevisionEntity(CustomRevisionListener.class) // <-- Связываем с Listener'ом
@Getter
@Setter
@ToString
public class CustomRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revinfo_seq")
    @SequenceGenerator(name = "revinfo_seq", sequenceName = "revinfo_seq", allocationSize = 1)
    @RevisionNumber
    private long id;

    @RevisionTimestamp
    private long timestamp;

    private Long userId;

    private String entityName;
}
