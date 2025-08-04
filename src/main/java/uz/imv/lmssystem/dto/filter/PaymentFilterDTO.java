package uz.imv.lmssystem.dto.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentFilterDTO {

    private LocalDate paidAfter;

    private LocalDate paidBefore;

    private Long studentId;

    private Long cashierId; //кассир Id
}
