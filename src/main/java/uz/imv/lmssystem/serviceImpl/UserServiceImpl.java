package uz.imv.lmssystem.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.imv.lmssystem.dto.UserDTO;
import uz.imv.lmssystem.dto.UserUpdateDTO;
import uz.imv.lmssystem.dto.response.ChangedRoleResponse;
import uz.imv.lmssystem.dto.response.UserInfoUpdateResponse;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.exceptions.EmptyFileException;
import uz.imv.lmssystem.exceptions.UnknownRoleException;
import uz.imv.lmssystem.exceptions.UserNotFoundException;
import uz.imv.lmssystem.mapper.UserMapper;
import uz.imv.lmssystem.repository.RoleRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.FileStorageService;
import uz.imv.lmssystem.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Override
    @Transactional
    public ChangedRoleResponse changeRole(Long userId, Long roleId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        String oldRole = user.getRole().getName();

        Role newRole = roleRepository.findById(roleId).orElseThrow(() -> new UnknownRoleException(roleId));

        user.setRole(newRole);

        userRepository.save(user);

        return new ChangedRoleResponse(
                user.getUsername(),
                oldRole,
                user.getRole().getName()
        );
    }


    @Override
    @Transactional
    public void deleteById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);

    }

    @Override
    @Transactional
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

        return new UserDTO(
                currentUser.getName(),
                currentUser.getSurname(),
                currentUser.getPhoneNumber(),
                currentUser.getUsername(),
                currentUser.getRole().getName(),
                bucketName + currentUser.getPhotoUrl()
        );
    }

    @Transactional
    @Override
    public void uploadAvatar(Long userId, MultipartFile file) {
        if (file.isEmpty())
            throw new EmptyFileException();

        User employee = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));


        String destinationPath = employee.getId().toString();

        String avatarFileName = fileStorageService.uploadFile(file, destinationPath);

        employee.setPhotoUrl(avatarFileName);

        userRepository.save(employee);
    }

}
