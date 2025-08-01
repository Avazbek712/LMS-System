package uz.imv.lmssystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.dto.SalaryPayRequest;
import uz.imv.lmssystem.dto.SalaryPayResponse;
import uz.imv.lmssystem.service.SalaryService;

/**
 * Created by Avazbek on 30/07/25 12:56
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salaries")
public class SalaryController {


    private final SalaryService salaryService;

    @PostMapping
    public ResponseEntity<SalaryPayResponse> pay(@Valid @RequestBody SalaryPayRequest request) {
        return ResponseEntity.ok(salaryService.pay(request));
    }

}
