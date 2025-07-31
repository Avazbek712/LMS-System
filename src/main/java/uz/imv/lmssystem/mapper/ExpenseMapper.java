package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.imv.lmssystem.dto.ExpenseDTO;
import uz.imv.lmssystem.entity.Expense;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseMapper {

    ExpenseDTO toDTO(Expense expense);

    List<ExpenseDTO> toDTO(List<Expense> expenses);

    Expense toEntity(ExpenseDTO dto);

    void updateEntity(ExpenseDTO dto, @MappingTarget Expense entity);
}
