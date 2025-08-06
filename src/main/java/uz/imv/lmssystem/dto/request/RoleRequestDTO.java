package uz.imv.lmssystem.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.PermissionsEnum;

import java.util.Set;

/**
 * Created by Avazbek on 05/08/25 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {
    @NotBlank(message = "Role must have a name")
    private String roleName;

    @Column(columnDefinition = "text")
    private String description;

    @NotNull(message = "You have to choose at least 1 permission for this role")
    private Set<PermissionsEnum> permissions;
}
