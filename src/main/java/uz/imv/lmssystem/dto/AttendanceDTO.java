package uz.imv.lmssystem.dto;

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
    private AttendanceStatus status;
    private Long studentId;
    private Long lessonId;
}