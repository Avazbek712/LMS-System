package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Avazbek on 29/07/25 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusResponse {

    private String studentName;

    private String studentSurname;

    private Boolean paymentStatus;

}
