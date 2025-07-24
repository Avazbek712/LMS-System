package uz.imv.lmssystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

/**
 * Created by Avazbek on 22/07/25 14:42
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course extends AbsLongEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long price;
}
