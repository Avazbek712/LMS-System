package uz.imv.lmssystem.mapper;

import org.mapstruct.*;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.dto.CourseDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    CourseDTO toDTO(Course course);

    List<CourseDTO> toDTO(List<Course> courses);

    Course toEntity(CourseDTO dto);

    void updateEntity(CourseDTO dto, @MappingTarget Course entity);

}