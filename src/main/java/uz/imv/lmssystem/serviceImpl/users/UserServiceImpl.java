package uz.imv.lmssystem.serviceImpl.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.imv.lmssystem.dto.UserDTO;
import uz.imv.lmssystem.dto.UserUpdateDTO;
import uz.imv.lmssystem.dto.auth.UpdatePasswordDTO;
import uz.imv.lmssystem.dto.request.ChangedRoleRequest;
import uz.imv.lmssystem.dto.response.ChangedRoleResponse;
import uz.imv.lmssystem.dto.response.UserInfoUpdateResponse;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.exceptions.EmptyFileException;
import uz.imv.lmssystem.exceptions.PasswordMismatchException;
import uz.imv.lmssystem.exceptions.UnknownRoleException;
import uz.imv.lmssystem.exceptions.UserNotFoundException;
import uz.imv.lmssystem.repository.users.RoleRepository;
import uz.imv.lmssystem.repository.users.UserRepository;
import uz.imv.lmssystem.service.files.FileStorageService;
import uz.imv.lmssystem.service.users.UserService;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Override
    @Transactional
    public ChangedRoleResponse changeRole(Long userId, ChangedRoleRequest dto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        String oldRole = user.getRole().getName();

        Role newRole = roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new UnknownRoleException(dto.getRoleId()));

        user.setRole(newRole);

        userRepository.save(user);
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

        if (Objects.isNull(currentUser.getPhotoUrl())) {

            return new UserDTO(
                    currentUser.getName(),
                    currentUser.getSurname(),
                    currentUser.getPhoneNumber(),
                    currentUser.getUsername(),
                    currentUser.getRole().getName(),
                    null
            );
        }


        return new UserDTO(
                currentUser.getName(),
                currentUser.getSurname(),
                currentUser.getPhoneNumber(),
                currentUser.getUsername(),
                currentUser.getRole().getName(),
                bucketName + "/" + currentUser.getPhotoUrl()
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

    @Override
    public UserDTO updatePassword(User currentUser, UpdatePasswordDTO dto) {

        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmPassword();


        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordMismatchException("Passwords don't match!");
        }

        if (oldPassword.equals(newPassword)) {
            throw new PasswordMismatchException("New password cannot be the same as the old one");
        }


        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new PasswordMismatchException("Old password is incorrect");

        }
        currentUser.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(currentUser);

        if (Objects.isNull(currentUser.getPhotoUrl())) {

            return new UserDTO(
                    currentUser.getName(),
                    currentUser.getSurname(),
                    currentUser.getPhoneNumber(),
                    currentUser.getUsername(),
                    currentUser.getRole().getName(),
                    null
            );
        }


        return new UserDTO(
                currentUser.getName(),
                currentUser.getSurname(),
                currentUser.getPhoneNumber(),
                currentUser.getUsername(),
                currentUser.getRole().getName(),
                bucketName +  "/" + currentUser.getPhotoUrl()
        );
    }

}
