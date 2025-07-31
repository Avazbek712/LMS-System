package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.IncomeCreateRequest;
import uz.imv.lmssystem.dto.IncomeCreateResponse;
import uz.imv.lmssystem.dto.IncomeDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Income;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.IncomeMapper;
import uz.imv.lmssystem.repository.IncomeRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.IncomeService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Avazbek on 29/07/25 14:40
 */
@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;

    @Override
    public IncomeCreateResponse create(IncomeCreateRequest request) {

        User employee = userRepository.
                findById(request.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Employee with ID : " + request.getEmployeeId() + " not found"));

        Income income = new Income();

        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setEmployee(employee);
        income.setCategory(request.getCategory());

        incomeRepository.save(income);

        return incomeMapper.toDTO(income);

    }

    @Override
    public IncomeDTO getById(Long id) {

        Income income = incomeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Income with ID : " + id + " not found"));

        return incomeMapper.toDto(income);
    }

    @Override
    public PageableDTO getAll(Integer page, Integer size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Income> pageIncome = incomeRepository.findAll(pageable);

        List<Income> incomes = pageIncome.getContent();

        if (incomes.isEmpty()) return new PageableDTO(size, 0L, 0, false, false, null);

        List<IncomeDTO> incomeDTOs = incomes.stream().map(incomeMapper::toDto).collect(Collectors.toList());

        return new PageableDTO(
                pageIncome.getSize(),
                pageIncome.getTotalElements(),
                pageIncome.getTotalPages(),
                !pageIncome.isLast(),
                !pageIncome.isFirst(),
                incomeDTOs
        );


    }


}
