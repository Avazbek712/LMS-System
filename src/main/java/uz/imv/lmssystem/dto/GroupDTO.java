package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.enums.Schedule;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * Created by Avazbek on 28/07/25 10:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO implements Serializable {

    private String groupName;

    private String courseName;

    private String teacherName;

    private String RoomNumber;

    private Integer numberOfStudents;

    private String schedule;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private GroupStatus status;


}
