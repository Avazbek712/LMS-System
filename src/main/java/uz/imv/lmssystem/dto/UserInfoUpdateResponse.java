package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 23/07/25 14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUpdateResponse {

    private String name;

    private String surname;

    private String phoneNumber;

}
