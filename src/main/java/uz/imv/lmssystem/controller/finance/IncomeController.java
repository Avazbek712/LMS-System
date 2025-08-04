package uz.imv.lmssystem.controller.finance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.request.IncomeCreateRequest;
import uz.imv.lmssystem.dto.response.IncomeCreateResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.service.IncomeService;

/**
 * Created by Avazbek on 29/07/25 17:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping
    public ResponseEntity<PageableDTO> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(incomeService.getAll(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(incomeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<IncomeCreateResponse> create(@Valid @RequestBody IncomeCreateRequest request) {

        return ResponseEntity.ok(incomeService.create(request));
    }
}
