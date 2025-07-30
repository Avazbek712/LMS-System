package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.imv.lmssystem.entity.Expense}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO implements Serializable {

    private String description;

    private BigDecimal amount;

    private ExpenseCategoryEnum category;

    private LocalDateTime date;

    private String employeeName;

    private String employeeSurname;

}