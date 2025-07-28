package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {


    @Mapping(target = "teacherSurname", source = "teacher.surname")
    @Mapping(target = "teacherName", source = "teacher.name")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "courseName", source = "course.name")
    GroupCreateResponse groupToCreateResponse(Group group);

}
