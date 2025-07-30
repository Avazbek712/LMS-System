package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.PaymentCreateRequest;
import uz.imv.lmssystem.dto.PaymentCreateResponse;
import uz.imv.lmssystem.dto.PaymentStatusResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.PaymentDTO;

public interface PaymentService {

    PaymentCreateResponse create(PaymentCreateRequest request);

    void delete(Long id);

    PaymentStatusResponse checkPaymentStatus(Long studentId);

    PageableDTO getAll(Integer page, Integer size);

    PaymentDTO getById(Long id);


}
