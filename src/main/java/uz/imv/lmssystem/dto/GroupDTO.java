package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.GroupStatus;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Avazbek on 28/07/25 10:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    private String groupName;

    private String courseName;

    private String teacherName;

    private String RoomNumber;

    private Integer numberOfStudents;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;
    
    private LocalTime endTime;

    private GroupStatus status;


}
