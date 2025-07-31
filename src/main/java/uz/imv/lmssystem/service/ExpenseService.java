package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {

    List<ExpenseDTO> getAll();

    ExpenseDTO getById(Long id);

    void deleteById(Long id);

    ExpenseDTO save(ExpenseDTO expenseDTO);

    ExpenseDTO update(Long id, ExpenseDTO expenseDTO);
}
