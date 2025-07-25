package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.enums.Schedule;

import java.time.LocalDate;

/**
 * Created by Avazbek on 24/07/25 14:46
 */
@Entity(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update groups set deleted=true where id=?")
public class Group extends AbsLongEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Schedule schedule;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private GroupStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
}
