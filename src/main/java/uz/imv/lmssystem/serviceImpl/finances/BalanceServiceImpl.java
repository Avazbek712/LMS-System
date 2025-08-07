package uz.imv.lmssystem.serviceImpl.finances;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.CurrentBalanceDTO;
import uz.imv.lmssystem.repository.*;
import uz.imv.lmssystem.service.finances.BalanceService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 28/07/25 16:28
 */
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final CompanyBalanceRepository companyBalanceRepository;
    private final ExpenseRepository expenseRepository;
    private final PaymentRepository paymentRepository;
    private final IncomeRepository incomeRepository;
    private final SalaryRepository salaryRepository;

    @Override
    public CurrentBalanceDTO getCurrentBalance() {

        BigDecimal initialBalance = companyBalanceRepository.getInitialBalance()
                .orElse(BigDecimal.ZERO);

        BigDecimal studentPayments = paymentRepository.getTotalPayments();

        BigDecimal totalIncome = incomeRepository.getTotalIncome();

        BigDecimal totalExpense = expenseRepository.getTotalExpense();

        BigDecimal totalSalaries = salaryRepository.getTotalSalaries();

        BigDecimal balance = initialBalance.add(studentPayments).add(totalIncome).subtract(totalExpense).subtract(totalSalaries);

        return new CurrentBalanceDTO(
                balance,
                LocalDateTime.now()
        );
    }
}
