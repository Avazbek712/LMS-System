package uz.imv.lmssystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.entity.Role;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO implements Serializable {
    @NotBlank(message = "first name must not be blank")
    private String name;

    @NotBlank(message = "last name must not be blank")
    private String surname;

    @NotBlank(message = "email must not be blank")
    private String username;

    @NotBlank(message = "phone number must not be blank")
    private String phoneNumber;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, max = 64, message = "Password must be between {min} and {max} characters")
    private String password;

    private Long roleId;
}