package uz.imv.lmssystem.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "DTO for filtering expenses",
        title = "Expense Filter DTO")
public class ExpenseFilterDTO {

    @Schema(description = "From date of the expense", example = "2023-01-01T00:00:00")
    private LocalDateTime fromDate;

    @Schema(description = "To date of the expense", example = "2023-01-31T23:59:59")
    private LocalDateTime toDate;

    @Schema(description = "Minimum amount of the expense", example = "100.00")
    private BigDecimal minAmount;

    @Schema(description = "Maximum amount of the expense", example = "1000.00")
    private BigDecimal maxAmount;


    @Schema(description = "Category of the expense", example = "TAXES")
    private ExpenseCategoryEnum category;

}
