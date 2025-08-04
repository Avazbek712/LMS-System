package uz.imv.lmssystem.controller.finance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.request.CreateExpenseRequest;
import uz.imv.lmssystem.dto.response.CreateExpenseResponse;
import uz.imv.lmssystem.dto.ExpenseDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.service.finances.ExpenseService;

/**
 * Created by Avazbek on 29/07/25 12:45
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    @PreAuthorize("hasAuthority('EXPENSE_READ')")
    public ResponseEntity<PageableDTO> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "10") Integer size) {

        return ResponseEntity.ok(expenseService.getAll(page, size));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('EXPENSE_READ')")
    public ResponseEntity<ExpenseDTO> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(expenseService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EXPENSE_CREATE')")
    public ResponseEntity<CreateExpenseResponse> create(@Valid @RequestBody CreateExpenseRequest request, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(expenseService.create(request, currentUser));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('EXPENSE_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
