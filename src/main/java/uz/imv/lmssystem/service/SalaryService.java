package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.SalaryPayRequest;
import uz.imv.lmssystem.dto.SalaryPayResponse;

public interface SalaryService {

         SalaryPayResponse pay(SalaryPayRequest request);

}
