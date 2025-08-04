package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 23/07/25 12:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangedRoleResponse implements Serializable {


    private String username;

    private String oldRole;

    private String newRole;

}
