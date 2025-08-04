package uz.imv.lmssystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 04/08/25 12:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    @NotBlank(message = "old password can't be blank")
    private String oldPassword;

    @NotBlank(message = "new password can't be blank")
    private String newPassword;

    @NotBlank(message = "confirm password can't be blank")
    private String confirmPassword;

}
