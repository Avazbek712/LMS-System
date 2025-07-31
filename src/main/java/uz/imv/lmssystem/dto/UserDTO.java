package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 23/07/25 14:42
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private String name;

    private String surname;

    private String phoneNumber;

    private String username;

    private String role;

    private String photoUrl;
}
