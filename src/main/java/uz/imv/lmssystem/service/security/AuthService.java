package uz.imv.lmssystem.service.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.imv.lmssystem.dto.auth.RefreshTokenRequest;
import uz.imv.lmssystem.dto.response.NewEmployeeResponse;
import uz.imv.lmssystem.dto.auth.LoginDTO;
import uz.imv.lmssystem.dto.auth.RegisterDTO;
import uz.imv.lmssystem.dto.auth.TokenDTO;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.PermissionsEnum;

public interface AuthService extends UserDetailsService {
    TokenDTO login(LoginDTO dto);

    NewEmployeeResponse createEmployee(RegisterDTO dto);

    TokenDTO refresh(RefreshTokenRequest token);

    boolean hasPermission(PermissionsEnum permission, User user);


}
