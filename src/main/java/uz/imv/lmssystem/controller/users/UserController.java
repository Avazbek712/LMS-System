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
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.service.users.UserService;

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

}
