package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.repository.CompanyBalanceRepository;
import uz.imv.lmssystem.repository.ExpenseRepository;
import uz.imv.lmssystem.repository.IncomeRepository;
import uz.imv.lmssystem.repository.PaymentRepository;
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

    @Override
    public BigDecimal getCurrentBalance() {

        BigDecimal initialBalance = companyBalanceRepository.getInitialBalance()
                .orElse(BigDecimal.ZERO);

        BigDecimal studentIncome = paymentRepository.getTotalPayments();

        BigDecimal totalIncome = incomeRepository.getTotalIncome();

        BigDecimal totalExpense = expenseRepository.getTotalExpense();

        return initialBalance.add(studentIncome).add(totalIncome).subtract(totalExpense);
    }
}
