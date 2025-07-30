package uz.imv.lmssystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

/**
 * Created by Avazbek on 25/07/25 10:42
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update student set deleted=true where id=?")
public class Student extends AbsLongEntity {


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean paymentStatus = false; //true -> paid | false -> not paid

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;


}
