package uz.imv.lmssystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

/**
 * Created by Avazbek on 24/07/25 11:18
 */
@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update room set deleted=true where id=?")
public class Room extends AbsLongEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private Short roomNumber;

    @Column( nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer desks;

    @Column(nullable = false)
    private Integer chairs;
}
