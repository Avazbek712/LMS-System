package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Avazbek on 30/07/25 12:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPayResponse implements Serializable {

    private String employeeName;

    private String employeeSurname;

    private BigDecimal amount;

    private LocalDate date;
}
