package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyKeyJavaClass;

/**
 * Created by Avazbek on 23/07/25 14:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "surname must not be blank")
    private String surname;

    @NotBlank(message = "phone number must not be blank")
    private String phoneNumber;


}
