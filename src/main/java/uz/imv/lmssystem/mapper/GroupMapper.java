package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.imv.lmssystem.dto.GroupDTO;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.enums.Schedule;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupMapper {


    @Mapping(target = "teacherSurname", source = "teacher.surname")
    @Mapping(target = "teacherName", source = "teacher.name")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "courseName", source = "course.name")
    GroupCreateResponse groupToCreateResponse(Group group);

    @Mapping(target = "startTime", source = "lessonStartTime")
    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(source = "students", target = "numberOfStudents", qualifiedByName = "studentsCount")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "endTime", source = "lessonStartTime")
    @Mapping(target = "teacherName", source = "teacher.name")
    @Mapping(target = "courseName", source = "course.name")
    @Mapping(target = "schedule", expression = "java(mapSchedule(group.getSchedule()))")
    GroupDTO toDTO(Group group);

    default String mapSchedule(Set<Schedule> schedule) {
        if (schedule == null || schedule.isEmpty()) {
            return null;
        }
        return schedule.stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Named("studentsCount")
    default int studentsCount(List<Student> students) {
        return students != null ? students.size() : 0;
    }


}
