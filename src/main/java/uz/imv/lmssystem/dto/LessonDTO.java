package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Avazbek on 04/08/25 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String teacherName;

    private String teacherSurname;

    private String groupName;

    private String courseName;

}
