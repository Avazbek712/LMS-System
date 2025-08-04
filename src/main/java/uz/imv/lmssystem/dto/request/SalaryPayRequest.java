package uz.imv.lmssystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Avazbek on 30/07/25 12:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPayRequest implements Serializable {

    @NotNull(message = "Employee ID can't be null")
    private Long employee;

    @NotNull(message = "Amount can't be null")
    private BigDecimal amount;

    private String description;

    @NotNull(message = "Date can't be null")
    private LocalDate date;
}
