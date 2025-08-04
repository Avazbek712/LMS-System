package uz.imv.lmssystem.serviceImpl.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.DashboardDTO;
import uz.imv.lmssystem.projection.DashboardStatsProjection;
import uz.imv.lmssystem.repository.DashboardRepository;
import uz.imv.lmssystem.service.DashboardService;

/**
 * Created by Avazbek on 31/07/25 11:18
 */
@Service
@RequiredArgsConstructor
public class DashboarServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public DashboardDTO getStatistics() {

        DashboardStatsProjection projection = dashboardRepository.getDashboardStatistics();

        return DashboardDTO.builder()
                .employeeCount(projection.getEmployeeCount())
                .activeStudentCount(projection.getActiveStudentCount())
                .activeGroupCount(projection.getActiveGroupCount())
                .debtorStudentCount(projection.getDebtorStudentCount())
                .paidInCurrentMonthCount(projection.getPaidInCurrentMonthCount())
                .build();
    }
}
