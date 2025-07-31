package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.repository.*;
import uz.imv.lmssystem.service.BalanceService;

import java.math.BigDecimal;

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
    public BigDecimal getCurrentBalance() {

        BigDecimal initialBalance = companyBalanceRepository.getInitialBalance()
                .orElse(BigDecimal.ZERO);

        BigDecimal studentPayments = paymentRepository.getTotalPayments();

        BigDecimal totalIncome = incomeRepository.getTotalIncome();

        BigDecimal totalExpense = expenseRepository.getTotalExpense();

        BigDecimal totalSalaries = salaryRepository.getTotalSalaries();

        return initialBalance.add(studentPayments).add(totalIncome).subtract(totalExpense).subtract(totalSalaries);
    }
}
