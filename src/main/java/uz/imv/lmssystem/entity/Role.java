package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.PermissionsEnum;

import java.util.Set;

/**
 * Created by Avazbek on 23/07/25 10:31
 */
@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends AbsLongEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection(targetClass = PermissionsEnum.class)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PermissionsEnum> permissions;


}
