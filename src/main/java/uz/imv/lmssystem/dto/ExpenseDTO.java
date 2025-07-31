package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.CategoryEnum;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.imv.lmssystem.entity.Expense}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO implements Serializable {

    private Long id;

    private String description;

    @NotBlank(message = "date can not be blank")
    private LocalDateTime date;

    private Long employeeId;

    @NotNull(message = "amount cannot be null")
    @Positive(message = "amount must be more then zero")
    private BigInteger amount;

    @NotBlank(message = "category can not be blank")
    private String category;

}