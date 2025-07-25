package uz.imv.lmssystem.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 18/07/25 12:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    private String accessToken;

  private String refreshToken;


}
