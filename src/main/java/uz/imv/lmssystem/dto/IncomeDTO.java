package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.IncomeCategoryEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.imv.lmssystem.entity.Income}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO implements Serializable {

    private String description;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Category can not be null")
    private IncomeCategoryEnum category;
}