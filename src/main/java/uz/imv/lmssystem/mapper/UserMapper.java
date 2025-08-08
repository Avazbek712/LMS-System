package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import uz.imv.lmssystem.dto.UserDTO;
import uz.imv.lmssystem.dto.response.RespUserDTO;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
    UserDTO toDTO(User user);

    @Named("roleToString")
    static String roleToString(Role role) {
        return role != null ? role.getName() : null;
    }

    @Mapping(target = "fullName", expression = "java(user.getName() + \" \" + user.getSurname())")
    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "deleted", source = "deleted")
    RespUserDTO toRespDTO(User user);

    List<RespUserDTO> toRespDTO(List<User> users);

}

