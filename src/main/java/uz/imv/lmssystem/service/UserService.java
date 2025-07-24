package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.*;
import uz.imv.lmssystem.dto.fildErrors.response.ChangedRoleResponse;
import uz.imv.lmssystem.dto.fildErrors.response.UserInfoUpdateResponse;
import uz.imv.lmssystem.entity.User;

/**
 * Created by Avazbek on 23/07/25 12:49
 */
public interface UserService {


    ChangedRoleResponse changeRole(Long id, ChangeRoleDTO role);

    void deleteById(long id);

    UserInfoUpdateResponse updateUser(User currentUser, UserUpdateDTO dto);

    UserDTO getAboutMe(User currentUser);
}
