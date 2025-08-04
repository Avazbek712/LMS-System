package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 23/07/25 12:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEmployeeResponse implements Serializable {

    private String username;

    private String password;

}
