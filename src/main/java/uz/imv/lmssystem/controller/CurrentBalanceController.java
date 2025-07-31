package uz.imv.lmssystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.service.BalanceService;

import java.math.BigDecimal;

/**
 * Created by Avazbek on 28/07/25 17:41
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/current-balance")
public class CurrentBalanceController {

    private final BalanceService balanceService;

    @GetMapping
    @PreAuthorize("hasAuthority('SEE_CURRENT_BALANCE')")
    public ResponseEntity<BigDecimal> getCurrentBalance() {
        return ResponseEntity.ok(balanceService.getCurrentBalance());
    }

}
