package uz.imv.lmssystem.mapper;

import org.mapstruct.*;
import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.entity.Attendance;
import uz.imv.lmssystem.mapper.resolvers.LessonResolver;
import uz.imv.lmssystem.mapper.resolvers.StudentResolver;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "lesson.id", target = "lessonId")
    AttendanceDTO toDTO(Attendance attendance);

    List<AttendanceDTO> toDTO(List<Attendance> attendances);

    @Mapping(target = "student", expression = "java(studentResolver.resolve(dto.getStudentId()))")
    @Mapping(target = "lesson", expression = "java(lessonResolver.resolve(dto.getLessonId()))")
    Attendance toEntity(AttendanceDTO dto,
                        @Context StudentResolver studentResolver,
                        @Context LessonResolver lessonResolver);

    @Mapping(target = "student", expression = "java(studentResolver.resolve(dto.getStudentId()))")
    @Mapping(target = "lesson", expression = "java(lessonResolver.resolve(dto.getLessonId()))")
    void updateEntity(AttendanceDTO dto,
                      @MappingTarget Attendance entity,
                      @Context StudentResolver studentResolver,
                      @Context LessonResolver lessonResolver);
}
