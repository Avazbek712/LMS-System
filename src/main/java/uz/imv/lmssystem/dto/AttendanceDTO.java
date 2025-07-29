package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.AttendanceStatus;

import java.io.Serializable;

/**
 * DTO for {@link uz.imv.lmssystem.entity.Attendance}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO implements Serializable {
    @NotBlank
    private AttendanceStatus status;
    @NotNull
    private Long studentId;
    @NotNull
    private Long lessonId;
}