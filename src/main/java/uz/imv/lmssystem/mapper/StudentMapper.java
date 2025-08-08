package uz.imv.lmssystem.mapper;

import org.mapstruct.*;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.StudentDebtors;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.mapper.resolvers.GroupResolver;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface StudentMapper {

    @Mapping(source = "group.id", target = "groupId")
    StudentDTO toDTO(Student student);

    List<StudentDTO> toDTO(List<Student> students);

    @Mapping(target = "studentName", source = "name")
    @Mapping(target = "studentSurname", source = "surname")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "debtAmount", source = "group.course.price")
    StudentDebtors toDebtors(Student students);


    @Mapping(target = "group", expression = "java(groupResolver.resolve(dto.getGroupId()))")
    void updateEntity(StudentDTO dto, @MappingTarget Student entity, @Context GroupResolver groupResolver);
}
