package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.request.IncomeCreateRequest;
import uz.imv.lmssystem.dto.response.IncomeCreateResponse;
import uz.imv.lmssystem.dto.IncomeDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface IncomeService {

    IncomeCreateResponse create(IncomeCreateRequest request);

    IncomeDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);
}
