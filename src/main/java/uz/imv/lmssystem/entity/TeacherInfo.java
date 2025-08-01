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

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/**
 * Created by Avazbek on 24/07/25 15:21
 */
@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update teacher_info set deleted=true where id=?")
public class TeacherInfo extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

}
