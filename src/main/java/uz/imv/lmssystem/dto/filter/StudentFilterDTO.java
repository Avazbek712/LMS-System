package uz.imv.lmssystem.dto.filter;

import lombok.Data;


@Data
public class StudentFilterDTO {

    private String fullName;

    private Long groupId;

    private Boolean paymentStatus;

    private String phoneNumber;
}
