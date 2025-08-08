package uz.imv.lmssystem.controller.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.imv.lmssystem.dto.auth.UpdatePasswordDTO;
import uz.imv.lmssystem.dto.UserDTO;
import uz.imv.lmssystem.dto.UserUpdateDTO;
import uz.imv.lmssystem.dto.filter.GroupFilterDTO;
import uz.imv.lmssystem.dto.filter.UserFilterDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.dto.response.RespUserDTO;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.service.users.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    @GetMapping("about-me")
    public ResponseEntity<UserDTO> aboutMe(@AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(userService.getAboutMe(currentUser));

    }

    @PatchMapping("change-role/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @Valid @RequestBody Long roleId) {

        return ResponseEntity.ok(userService.changeRole(id, roleId));
    }

    @PutMapping("update-user")
    public ResponseEntity<?> updateInfoAboutMe(@AuthenticationPrincipal User currentUser, @Valid @RequestBody UserUpdateDTO dto) {

        return ResponseEntity.ok(userService.updateUser(currentUser, dto));
    }


    @DeleteMapping("delete-employee/{id}")
    @PreAuthorize("hasAuthority('STUDENT_DELETE')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("upload-avatar")
    public ResponseEntity<Void> uploadAvatar(@AuthenticationPrincipal User currentUser, @RequestParam("file") MultipartFile file) {

        userService.uploadAvatar(currentUser.getId(), file);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("update-password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal User currentUser, @Valid @RequestBody UpdatePasswordDTO dto) {

        return ResponseEntity.ok(userService.updatePassword(currentUser, dto));
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    @GetMapping("/filter")
    public PageableDTO filterGroups(@Valid @RequestBody UserFilterDTO filter,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return userService.getFilteredEmployees(filter, page, size);
    }

}
