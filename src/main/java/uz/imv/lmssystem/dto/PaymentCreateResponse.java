package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Avazbek on 29/07/25 11:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateResponse {

    private String studentName;

    private String studentSurname;

    private BigDecimal amount;

}
