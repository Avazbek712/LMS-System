package uz.imv.lmssystem.service.users;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.imv.lmssystem.dto.*;
import uz.imv.lmssystem.dto.auth.UpdatePasswordDTO;
import uz.imv.lmssystem.dto.filter.GroupFilterDTO;
import uz.imv.lmssystem.dto.filter.UserFilterDTO;
import uz.imv.lmssystem.dto.response.ChangedRoleResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.dto.response.RespUserDTO;
import uz.imv.lmssystem.dto.response.UserInfoUpdateResponse;
import uz.imv.lmssystem.entity.User;

import java.util.List;


public interface UserService {


    ChangedRoleResponse changeRole(Long userId, Long roleId);

    void deleteById(long id);

    UserInfoUpdateResponse updateUser(User currentUser, UserUpdateDTO dto);

    UserDTO getAboutMe(User currentUser);

    @Transactional
    void uploadAvatar(Long userId, MultipartFile file);

    UserDTO updatePassword(User currentUser, UpdatePasswordDTO dto);

    PageableDTO getFilteredEmployees(UserFilterDTO filter, int page, int size);  //( get Filtered Employees/Users)
}
