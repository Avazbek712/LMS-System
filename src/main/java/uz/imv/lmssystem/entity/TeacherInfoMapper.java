package uz.imv.lmssystem.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.imv.lmssystem.dto.TeacherInfoDTO;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherInfoMapper {

    @Mapping(target = "teacherSurname", source = "teacher.surname")
    @Mapping(target = "teacherName", source = "teacher.name")
    @Mapping(target = "courseName", source = "course.name")
    TeacherInfoDTO toDTO(TeacherInfo teacherInfo);
}