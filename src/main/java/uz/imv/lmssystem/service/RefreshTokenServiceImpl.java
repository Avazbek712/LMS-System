package uz.imv.lmssystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.entity.RefreshToken;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.repository.RefreshTokenRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.security.JwtService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Created by Avazbek on 23/07/25 15:45
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        token.setToken(jwtService.generateRefreshToken(user));
        return refreshTokenRepository.save(token);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

}
