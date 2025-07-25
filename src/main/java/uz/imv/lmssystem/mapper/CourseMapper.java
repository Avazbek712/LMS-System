package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.dto.CourseDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    CourseDTO toDto(Course course);
}