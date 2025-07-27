package uz.imv.lmssystem.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * Created by Avazbek on 24/07/25 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRequest {
    @NotBlank(message = "name must not be blank")
    private String name;

    @NotNull(message = "course ID must not be null")
    private Long courseId;

    @NotNull(message = "teacher ID must not be null")
    private Long teacherId;

    @NotNull(message = "room ID must not be null")
    private Long roomId;

    @NotNull(message = "start date must not be null")
    @FutureOrPresent(message = "start date should be present or future")
    private LocalDate startDate;

    @NotNull(message = "end date must not be null")
    private LocalDate endDate;

    @NotNull(message = "lesson start time must not be null")
    private LocalTime lessonStartTime;

    @NotNull(message = "lesson start time must not be null")
    private LocalTime lessonEndTime;

    @NotEmpty(message = "schedule must not be empty")
    private Set<Schedule> schedule;

}
