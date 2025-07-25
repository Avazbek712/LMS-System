package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 23/07/25 12:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangedRoleResponse {

    private String username;

    private String oldRole;

    private String newRole;

}
