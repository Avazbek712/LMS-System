package uz.imv.lmssystem.serviceImpl.finances;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.request.SalaryPayRequest;
import uz.imv.lmssystem.dto.response.SalaryPayResponse;
import uz.imv.lmssystem.entity.Salary;
import uz.imv.lmssystem.mapper.SalaryMapper;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.exceptions.UserNotFoundException;
import uz.imv.lmssystem.repository.SalaryRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.finances.BalanceService;
import uz.imv.lmssystem.service.finances.SalaryService;

import java.math.BigDecimal;

/**
 * Created by Avazbek on 30/07/25 12:34
 */
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final BalanceService balanceService;
    private final UserRepository userRepository;
    private final SalaryMapper salaryMapper;
    private final SalaryRepository salaryRepository;

    @Override
    public SalaryPayResponse pay(SalaryPayRequest request) {

        BigDecimal currentBalance = balanceService.getCurrentBalance();

        if (currentBalance.compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Not enough money.Current balance : " + currentBalance);
        }

        User employee = userRepository.
                findById(request.getEmployee()).orElseThrow(() -> new UserNotFoundException("Employee ID : " + request.getEmployee() + " not found!"));

        Salary salary = new Salary();

        salary.setEmployee(employee);
        salary.setDescription(request.getDescription());
        salary.setDate(request.getDate());
        salary.setAmount(request.getAmount());

        salaryRepository.save(salary);

        return salaryMapper.toDto(salary);
    }
}
