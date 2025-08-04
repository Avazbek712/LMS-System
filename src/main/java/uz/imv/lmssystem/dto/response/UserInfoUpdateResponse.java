package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 23/07/25 14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUpdateResponse implements Serializable {

    private String name;

    private String surname;

    private String phoneNumber;

}
