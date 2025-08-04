package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.request.CreateExpenseRequest;
import uz.imv.lmssystem.dto.response.CreateExpenseResponse;
import uz.imv.lmssystem.dto.ExpenseDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.User;

public interface ExpenseService {

    CreateExpenseResponse create(CreateExpenseRequest request , User currentUser);

    void delete(Long id);

    ExpenseDTO findById(Long id);

    PageableDTO getAll(Integer page, Integer size);
}
