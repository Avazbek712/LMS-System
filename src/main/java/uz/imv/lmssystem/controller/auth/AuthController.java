package uz.imv.lmssystem.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.dto.auth.RefreshTokenRequest;
import uz.imv.lmssystem.dto.response.NewEmployeeResponse;
import uz.imv.lmssystem.dto.auth.LoginDTO;
import uz.imv.lmssystem.dto.auth.RegisterDTO;
import uz.imv.lmssystem.dto.auth.TokenDTO;
import uz.imv.lmssystem.service.security.AuthService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    public ResponseEntity<NewEmployeeResponse> createEmployee(@Valid @RequestBody RegisterDTO dto) {

        return ResponseEntity.ok(authService.createEmployee(dto));
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenDTO> refresh(@Valid @RequestBody
                                            RefreshTokenRequest token) {
        return ResponseEntity.ok(authService.refresh(token));
    }

}
