package uz.imv.lmssystem.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.dto.ExpenseDTO;
import uz.imv.lmssystem.entity.Expense;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.CategoryEnum;
import uz.imv.lmssystem.mapper.ExpenseMapper;
import uz.imv.lmssystem.repository.ExpenseRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.ExpenseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseDTO> getAll() {
        return expenseMapper.toDTO(expenseRepository.findAll());
    }

    @Override
    public ExpenseDTO getById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        return expenseMapper.toDTO(expense);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional
    public ExpenseDTO save(ExpenseDTO dto) {
        Expense expense = expenseMapper.toEntity(dto);

        if (dto.getEmployeeId() != null) {
            User user = userRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getEmployeeId()));
            expense.setEmployee(user);
        }

        expense.setCategory(CategoryEnum.valueOf(dto.getCategory()));
        Expense saved = expenseRepository.save(expense);
        return expenseMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ExpenseDTO update(Long id, ExpenseDTO dto) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        expenseMapper.updateEntity(dto, existing);

        if (dto.getEmployeeId() != null) {
            User user = userRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getEmployeeId()));
            existing.setEmployee(user);
        }

        existing.setCategory(CategoryEnum.valueOf(dto.getCategory()));
        Expense updated = expenseRepository.save(existing);
        return expenseMapper.toDTO(updated);
    }
}