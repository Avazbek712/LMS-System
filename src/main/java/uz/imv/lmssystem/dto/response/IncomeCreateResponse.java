package uz.imv.lmssystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class IncomeCreateResponse implements Serializable {

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String employeeSurname;

    private String employeeName;

    private BigDecimal amount;

    private IncomeCategoryEnum category;


}
