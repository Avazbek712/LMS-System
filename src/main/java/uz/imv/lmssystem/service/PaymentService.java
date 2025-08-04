package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.PaymentDTO;
import uz.imv.lmssystem.dto.filter.PaymentFilterDTO;
import uz.imv.lmssystem.dto.request.PaymentCreateRequest;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.dto.response.PaymentCreateResponse;
import uz.imv.lmssystem.dto.response.PaymentStatusResponse;

public interface PaymentService {

    PaymentCreateResponse create(PaymentCreateRequest request);

    void delete(Long id);

    PaymentStatusResponse checkPaymentStatus(Long studentId);

    PageableDTO getAll(Integer page, Integer size);

    PaymentDTO getById(Long id);

    PageableDTO getFilteredPayments(PaymentFilterDTO filter, int page, int size);
}
