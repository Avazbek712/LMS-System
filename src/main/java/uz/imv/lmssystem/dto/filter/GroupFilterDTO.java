package uz.imv.lmssystem.dto.filter;

import lombok.Data;
import uz.imv.lmssystem.enums.GroupStatus;

@Data
public class GroupFilterDTO {

    private String name;

    private Long teacherId;

    private Long courseId;

    private GroupStatus status;

}
