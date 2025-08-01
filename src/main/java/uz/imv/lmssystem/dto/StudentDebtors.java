package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Avazbek on 31/07/25 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDebtors {


    private String studentName;

    private String studentSurname;

    private String groupName;

    private BigDecimal debtAmount;



}
