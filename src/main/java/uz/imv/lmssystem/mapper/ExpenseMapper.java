package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.imv.lmssystem.dto.CreateExpenseResponse;
import uz.imv.lmssystem.dto.ExpenseDTO;
import uz.imv.lmssystem.entity.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "employeeSurname", source = "employee.surname")
    CreateExpenseResponse toResponse(Expense entity);

    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "employeeSurname", source = "employee.surname")
    ExpenseDTO toDto(Expense expense);
}
