package uz.imv.lmssystem.service.finances;

import uz.imv.lmssystem.dto.CurrentBalanceDTO;

import java.math.BigDecimal;

public interface BalanceService {

    CurrentBalanceDTO getCurrentBalance();

}
