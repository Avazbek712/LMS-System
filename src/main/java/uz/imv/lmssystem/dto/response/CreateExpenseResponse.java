package uz.imv.lmssystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 28/07/25 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpenseResponse implements Serializable {

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String employeeName;

    private String employeeSurname;

    private BigDecimal amount;

    private ExpenseCategoryEnum category;
}