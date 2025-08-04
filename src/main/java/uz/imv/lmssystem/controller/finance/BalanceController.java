package uz.imv.lmssystem.controller.finance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.service.finances.BalanceService;

/**
 * Created by Avazbek on 29/07/25 15:41
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/balance")
public class BalanceController {


    private final BalanceService balanceService;

    @GetMapping("current")
    @PreAuthorize("hasAuthority('BALANCE_SEE')")
    public ResponseEntity<?> getCurrentBalance() {
        return ResponseEntity.ok(balanceService.getCurrentBalance());
    }

}
