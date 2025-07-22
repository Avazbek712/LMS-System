package uz.imv.lmssystem.service.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.imv.lmssystem.dto.auth.LoginDTO;
import uz.imv.lmssystem.dto.auth.RegisterDTO;
import uz.imv.lmssystem.dto.auth.TokenDTO;

public interface AuthService extends UserDetailsService {
    TokenDTO login(LoginDTO dto);

    TokenDTO createEmployee(RegisterDTO dto);
}
