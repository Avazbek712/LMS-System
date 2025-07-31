package uz.imv.lmssystem.dto.filter;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;


@Data
public class StudentFilterDTO {

    @Parameter(description = "Full name or partial match (first name or last name)")
    private String fullName;

    @Parameter(description = "Group ID")
    private Long groupId;

    @Parameter(description = "Payment status (true = paid, false = debtor)")
    private Boolean paymentStatus;

    @Parameter(description = "Phone number or part of it")
    private String phoneNumber;
}
