package uz.imv.lmssystem.controller.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.dto.DashboardDTO;
import uz.imv.lmssystem.service.dashboard.DashboardService;

/**
 * Created by Avazbek on 31/07/25 11:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDTO> getStatistics() {

        return ResponseEntity.ok(dashboardService.getStatistics());
    }

}
