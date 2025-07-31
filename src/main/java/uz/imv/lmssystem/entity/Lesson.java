package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Avazbek on 25/07/25 10:26
 */
@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update lesson set deleted=true where id=?")
public class Lesson extends AbsLongEntity {


    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Group group;

}
