package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Created by Avazbek on 30/07/25 12:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPayResponse {

    private String employeeName;

    private String employeeSurname;

    private BigDecimal amount;

    private LocalDate date;
}
