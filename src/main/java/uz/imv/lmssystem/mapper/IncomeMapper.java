package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.imv.lmssystem.dto.response.IncomeCreateResponse;
import uz.imv.lmssystem.dto.IncomeDTO;
import uz.imv.lmssystem.entity.Income;

@Mapper(componentModel = "spring")
public interface IncomeMapper {


    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "employeeSurname", source = "employee.surname")
    IncomeCreateResponse toDTO(Income income);

    IncomeDTO toDto(Income income);
}
