package uz.imv.lmssystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Created by Avazbek on 29/07/25 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateRequest {

    @NotNull(message = "Student ID cannot be blank")
    private Long studentId;

    private Long cashierId;

    @NotNull(message = "Amount cannot be null or negative")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    private YearMonth paymentFor;

}
