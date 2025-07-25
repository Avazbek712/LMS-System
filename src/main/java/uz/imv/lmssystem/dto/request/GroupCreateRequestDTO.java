package uz.imv.lmssystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.enums.Schedule;

import java.time.LocalDate;

/**
 * Created by Avazbek on 24/07/25 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRequestDTO {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotNull
    private Schedule schedule;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "status must not be null")
    private GroupStatus status;

    @NotNull(message = "course id must not be null")
    private Long courseId;

    @NotNull(message = "teacher id must not be null")
    private Long teacherId;

    private Long roomId;

}
