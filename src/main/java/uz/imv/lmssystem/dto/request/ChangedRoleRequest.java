package uz.imv.lmssystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 06/08/25 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangedRoleRequest {


    @NotNull(message = "role id must not be null")
    private Long roleId;


}
