package uz.imv.lmssystem.dto;

/**
 * Created by Avazbek on 06/08/25 16:46
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfoDTO {

    private String teacherName;

    private String teacherSurname;

    private String courseName;
}
