package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 23/07/25 12:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEmployeeResponse {

    private String username;

    private String password;

}
