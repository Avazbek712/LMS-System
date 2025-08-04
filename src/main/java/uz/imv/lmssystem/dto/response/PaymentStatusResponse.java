package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 29/07/25 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusResponse implements Serializable {

    private String studentName;

    private String studentSurname;

    private Boolean paymentStatus;

}
