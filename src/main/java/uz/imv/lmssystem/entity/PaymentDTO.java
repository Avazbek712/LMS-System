package uz.imv.lmssystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Payment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements Serializable {

    private String studentName;

    private String studentSurname;

    private String employeeName;

    private String employeeSurname;

    private BigDecimal amount;

    private LocalDateTime paymentDate;
}