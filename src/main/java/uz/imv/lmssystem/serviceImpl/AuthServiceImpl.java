package uz.imv.lmssystem.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.auth.LoginDTO;
import uz.imv.lmssystem.dto.auth.RefreshTokenRequest;
import uz.imv.lmssystem.dto.auth.RegisterDTO;
import uz.imv.lmssystem.dto.auth.TokenDTO;
import uz.imv.lmssystem.dto.response.NewEmployeeResponse;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.PermissionsEnum;
import uz.imv.lmssystem.exceptions.InvalidTokenException;
import uz.imv.lmssystem.exceptions.UnknownRoleException;
import uz.imv.lmssystem.exceptions.UserAlreadyExistException;
import uz.imv.lmssystem.exceptions.UserNotFoundException;
import uz.imv.lmssystem.repository.RoleRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.security.AuthService;
import uz.imv.lmssystem.service.security.JwtService;

import java.security.Permission;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public TokenDTO login(LoginDTO dto) {
        log.info("User '{}' attempting to log in", dto.getUsername());

        String email = dto.getUsername();
        String password = dto.getPassword();

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (!authenticate.isAuthenticated()) {
            log.warn("Failed login attempt for user with email '{}': bad credentials", email);
            throw new BadCredentialsException("Invalid credentials");
        }

        User user = (User) authenticate.getPrincipal();

        String accessToken = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        log.info("User with email '{}' successfully signed in", email);

        return new TokenDTO(
                accessToken,
                refreshToken
        );
    }

    @Override
    @Transactional
    public NewEmployeeResponse createEmployee(RegisterDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            log.warn("Username :  '{}' already exists", dto.getUsername());
            throw new UserAlreadyExistException(dto.getUsername());
        }


        Role role = roleRepository
                .findById(dto.getRoleId())
                .orElseThrow(() -> new UnknownRoleException(dto.getRoleId()));


        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        log.info("User '{}' is registered successfully with role '{}' ", user.getUsername(), role.getName());


        return new NewEmployeeResponse(
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    public TokenDTO refresh(RefreshTokenRequest token) {
        String refreshToken = token.getRefreshToken();

        String username = jwtService.extractUsernameFromRefreshToken(refreshToken);

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!user.getRefreshToken().equals(refreshToken) || !jwtService.isRefreshTokenValid(refreshToken, user)) {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }
        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new TokenDTO(
                newAccessToken,
                newRefreshToken
        );
    }

    @Override
    public boolean hasPermission(PermissionsEnum permission, User user) {
        return user.getRole().getPermissions()
                .stream()
                .anyMatch(p -> p.name().equals(permission.name()));

    }

}
