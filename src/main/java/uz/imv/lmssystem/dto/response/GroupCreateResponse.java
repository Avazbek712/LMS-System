package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.enums.Schedule;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Avazbek on 24/07/25 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateResponse {


    private String name;

    private Set<Schedule> schedule;

    private LocalDate startDate;

    private LocalDate endDate;

    private GroupStatus status;

    private String courseName;

    private String teacherName;

    private String teacherSurname;

    private Long roomId;

}
