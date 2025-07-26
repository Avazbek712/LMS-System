package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.*;
import uz.imv.lmssystem.dto.response.ChangedRoleResponse;
import uz.imv.lmssystem.dto.response.UserInfoUpdateResponse;
import uz.imv.lmssystem.entity.User;


public interface UserService {


    ChangedRoleResponse changeRole(Long userId, Long roleId);

    void deleteById(long id);

    UserInfoUpdateResponse updateUser(User currentUser, UserUpdateDTO dto);

    UserDTO getAboutMe(User currentUser);
}
