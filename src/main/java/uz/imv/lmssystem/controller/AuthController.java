package uz.imv.lmssystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.dto.NewEmployeeResponse;
import uz.imv.lmssystem.dto.RefreshTokenRequest;
import uz.imv.lmssystem.dto.auth.LoginDTO;
import uz.imv.lmssystem.dto.auth.RegisterDTO;
import uz.imv.lmssystem.dto.auth.TokenDTO;
import uz.imv.lmssystem.entity.RefreshToken;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.service.RefreshTokenService;
import uz.imv.lmssystem.service.RefreshTokenServiceImpl;
import uz.imv.lmssystem.service.security.AuthService;
import uz.imv.lmssystem.service.security.JwtService;

import java.time.Instant;

/**
 * Created by Avazbek on 22/07/25 11:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;
    private final RefreshTokenServiceImpl refreshTokenServiceImpl;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("register")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<NewEmployeeResponse> createEmployee(@Valid @RequestBody
                                                              RegisterDTO dto) {

        return ResponseEntity.ok(authService.createEmployee(dto));
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody
                                          LoginDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
//        String requestToken = request.getRefreshToken();
//
//        RefreshToken refreshToken = refreshTokenService.findByToken(requestToken)
//                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//
//        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
//            throw new RuntimeException("Refresh token expired");
//        }
//
//        User user = refreshToken.getUser();
//        String newAccessToken = jwtService.generateToken(user);
//
//
//
//
//
//        return ResponseEntity.ok(new TokenDTO(newAccessToken, refreshToken.getToken()));
//    }
//}

}
