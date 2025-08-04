package uz.imv.lmssystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.IncomeCategoryEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 29/07/25 14:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeCreateRequest implements Serializable {

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @NotNull(message = "Employee can not be null")
    private Long employeeId;

    @NotNull(message = "Employee can not be null")
    private BigDecimal amount;

    @NotNull(message = "Category can not be null")
    private IncomeCategoryEnum category;
}
