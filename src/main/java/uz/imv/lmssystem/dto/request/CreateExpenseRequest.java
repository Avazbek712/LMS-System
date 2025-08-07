package uz.imv.lmssystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 28/07/25 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpenseRequest implements Serializable {

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private ExpenseCategoryEnum category;


}
