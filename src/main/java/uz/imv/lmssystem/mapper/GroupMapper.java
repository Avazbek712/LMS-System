package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {


    GroupCreateResponse groupToCreateResponse(Group group);

}
