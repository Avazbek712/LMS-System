package uz.imv.lmssystem.projection;

/**
 * Created by Avazbek on 31/07/25 11:06
 */
public interface DashboardStatsProjection {

    Long getEmployeeCount();
    Long getActiveStudentCount();
    Long getActiveGroupCount();
    Long getDebtorStudentCount();
    Long getPaidInCurrentMonthCount();

}
