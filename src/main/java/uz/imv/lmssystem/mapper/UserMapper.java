package uz.imv.lmssystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.imv.lmssystem.dto.UserDTO;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
    UserDTO toDTO(User user);

    @Named("roleToString")
    static String roleToString(Role role) {
        return role != null ? role.getName() : null;
    }


}
