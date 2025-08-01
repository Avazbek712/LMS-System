package uz.imv.lmssystem.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.PaymentCreateRequest;
import uz.imv.lmssystem.dto.PaymentCreateResponse;
import uz.imv.lmssystem.dto.filter.PaymentFilterDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.service.PaymentService;

/**
 * Created by Avazbek on 29/07/25 12:07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAuthority('PAYMENT_CREATE')")
    public ResponseEntity<PaymentCreateResponse> create(@RequestBody PaymentCreateRequest request) {

        return ResponseEntity.ok(paymentService.create(request));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('PAYMENT_DELETE')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        paymentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("status/{studentId}")
    @PreAuthorize("hasAnyAuthority('PAYMENT_READ', 'PAYMENT_READ_DETAILS')")
    public ResponseEntity<?> getStudentStatus(@PathVariable("studentId") Long id) {

        return ResponseEntity.ok(paymentService.checkPaymentStatus(id));
    }

    @GetMapping("{paymentId}")
    @PreAuthorize("hasAnyAuthority('PAYMENT_READ', 'PAYMENT_READ_DETAILS')")
    public ResponseEntity<?> getOne(@PathVariable("paymentId") Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('PAYMENT_READ', 'PAYMENT_READ_DETAILS')")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(paymentService.getAll(page, size));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('PAYMENT_READ')")
    public PageableDTO filterPayments(
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            PaymentFilterDTO filter) {
        return paymentService.getFilteredPayments(filter, page, size);
    }

}
