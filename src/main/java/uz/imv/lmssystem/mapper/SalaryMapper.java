package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.imv.lmssystem.dto.response.SalaryPayResponse;
import uz.imv.lmssystem.entity.Salary;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalaryMapper {
    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "employeeSurname", source = "employee.surname")
    SalaryPayResponse toDto(Salary salary);
}