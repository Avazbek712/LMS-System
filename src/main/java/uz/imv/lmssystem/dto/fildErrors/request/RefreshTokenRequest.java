package uz.imv.lmssystem.dto.fildErrors.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 23/07/25 15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    private String refreshToken;
}
