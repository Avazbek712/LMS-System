package uz.imv.lmssystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 18/07/25 12:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "email must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, max = 64, message = "Password must be between {min} and {max} characters")
    private String password;

}
