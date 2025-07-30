package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.IncomeCreateRequest;
import uz.imv.lmssystem.dto.IncomeCreateResponse;
import uz.imv.lmssystem.dto.IncomeDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface IncomeService {

    IncomeCreateResponse create(IncomeCreateRequest request);

    IncomeDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);
}
