package uz.imv.lmssystem.serviceImpl.finances;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.dto.ExpenseDTO;
import uz.imv.lmssystem.dto.request.CreateExpenseRequest;
import uz.imv.lmssystem.dto.response.CreateExpenseResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Expense;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.ExpenseMapper;
import uz.imv.lmssystem.repository.ExpenseRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.finances.BalanceService;
import uz.imv.lmssystem.service.finances.ExpenseService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Avazbek on 28/07/25 15:21
 */
@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {


    private final UserRepository userRepository;
    private final BalanceService balanceService;
    private final ExpenseMapper expenseMapper;
    private final ExpenseRepository expenseRepository;

    @Override
    @Transactional
    public CreateExpenseResponse create(CreateExpenseRequest request, User currentUser) {

        User employee = userRepository.findById(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("User with id : " + "not found"));


        BigDecimal currentBalance = balanceService.getCurrentBalance().getCurrentBalanceInBigDecimal();

        BigDecimal requestedAmount = request.getAmount();

        if (currentBalance.compareTo(requestedAmount) < 0) {
            throw new IllegalArgumentException("Insufficient funds. Current balance: " + currentBalance + ", required: " + requestedAmount);
        }
        Expense expense = new Expense();

        expense.setDescription(request.getDescription());
        expense.setEmployee(employee);
        expense.setCategory(request.getCategory());
        expense.setAmount(requestedAmount);

        expenseRepository.save(expense);

        return expenseMapper.toResponse(expense);
    }

    @Override
    public void delete(Long id) {

        expenseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Expense with id : " + id + " not found"));

        expenseRepository.deleteById(id);
    }


    @Override
    public ExpenseDTO findById(Long id) {

        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Expense with id : " + id + " not found"));

        return expenseMapper.toDto(expense);
    }


    @Override
    public PageableDTO getAll(Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Expense> pageInfo = expenseRepository.findAll(pageable);
        List<Expense> content = pageInfo.getContent();

        List<ExpenseDTO> list = content.stream().map(expenseMapper::toDto).toList();

        if (list.isEmpty()) return new PageableDTO(size, 0L, 0, false, false, null);

        return new PageableDTO(
                pageInfo.getSize(),
                pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),
                !pageInfo.isLast(),
                !pageInfo.isFirst(),
                list
        );
    }
}
