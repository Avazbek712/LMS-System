package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.AttendanceStatus;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceStatusUpdateDTO implements Serializable {

    @NotBlank
    private AttendanceStatus status;


}
