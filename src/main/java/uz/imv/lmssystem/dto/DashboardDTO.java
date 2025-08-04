package uz.imv.lmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 31/07/25 11:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDTO implements Serializable {

    private Long employeeCount;

    private Long activeStudentCount;

    private Long activeGroupCount;

    private Long debtorStudentCount;

    private Long paidInCurrentMonthCount;
}
