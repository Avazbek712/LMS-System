package uz.imv.lmssystem.service.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.fildErrors.response.NewEmployeeResponse;
import uz.imv.lmssystem.dto.auth.LoginDTO;
import uz.imv.lmssystem.dto.auth.RegisterDTO;
import uz.imv.lmssystem.dto.auth.TokenDTO;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.exceptions.UnknownRoleException;
import uz.imv.lmssystem.exceptions.UserAlreadyExistException;
import uz.imv.lmssystem.repository.RoleRepository;
import uz.imv.lmssystem.repository.UserRepository;


/**
 * Created by Avazbek on 18/07/25 12:07
 */
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


    @PreAuthorize("hasRole('ADMIN')")
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

        String token = jwtService.generateToken(user);

        long expiration = jwtService.extractExpiration(token);

        log.info("User with email '{}' successfully signed in", email);

        return new TokenDTO(
                token,
                expiration
        );
    }

    @Override
    public NewEmployeeResponse createEmployee(RegisterDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            log.warn("Email :  '{}' already exists", dto.getUsername());
            throw new UserAlreadyExistException(dto.getUsername());
        }


        Role role = roleRepository
                .findByName(dto.getRoleName())
                .orElseThrow(() -> new UnknownRoleException(dto.getRoleName()));


        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        long expiration = jwtService.extractExpiration(token);

        log.info("User '{}' is registered successfully with role '{}' ", user.getUsername(), role.getName());


        return new NewEmployeeResponse(
                user.getUsername(),
                user.getPassword()
        );


    }
}
