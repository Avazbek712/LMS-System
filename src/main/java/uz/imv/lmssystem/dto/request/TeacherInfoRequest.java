package uz.imv.lmssystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 07/08/25 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfoRequest implements Serializable {

    @NotNull(message = "teacher ID can not be null")
    private Long teacherId;

    @NotNull(message = "course ID can not be null")
    private Long courseId;

}
