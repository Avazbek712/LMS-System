package uz.imv.lmssystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

/**
 * Created by Avazbek on 24/07/25 11:18
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room extends AbsLongEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private Short roomNumber;

    private int capacity;

    private Integer desks;

    private Integer chairs;
}
