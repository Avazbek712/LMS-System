package uz.imv.lmssystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 28/07/25 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpenseRequest {

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private ExpenseCategoryEnum category;


}
