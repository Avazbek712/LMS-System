package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.PermissionsEnum;

import java.util.Set;

/**
 * Created by Avazbek on 05/08/25 14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private String roleName;

    private String description;

    private Set<PermissionsEnum> permissions;

}
