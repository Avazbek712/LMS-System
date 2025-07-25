package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.*;
import uz.imv.lmssystem.dto.response.ChangedRoleResponse;
import uz.imv.lmssystem.dto.response.UserInfoUpdateResponse;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.exceptions.UnknownRoleException;
import uz.imv.lmssystem.exceptions.UserNotFoundException;
import uz.imv.lmssystem.mapper.UserMapper;
import uz.imv.lmssystem.repository.RoleRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.UserService;

/**
 * Created by Avazbek on 23/07/25 12:51
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public ChangedRoleResponse changeRole(Long id, ChangeRoleDTO role) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        String oldRole = user.getRole().getName();

        Role newRole = roleRepository.findByName(role.getRoleName()).orElseThrow(() -> new UnknownRoleException(role.getRoleName()));

        user.setRole(newRole);

        userRepository.save(user);

        return new ChangedRoleResponse(
                user.getUsername(),
                oldRole,
                user.getRole().getName()
        );
    }


    @Override
    public void deleteById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);

    }

    @Override
    public UserInfoUpdateResponse updateUser(User currentUser, UserUpdateDTO dto) {

        Long id = currentUser.getId();

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));


        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.save(user);

        return new UserInfoUpdateResponse(
                user.getName(),
                user.getSurname(),
                user.getPhoneNumber()
        );
    }

    @Override
    public UserDTO getAboutMe(User currentUser) {
        return userMapper.toDto(currentUser);

    }


}
