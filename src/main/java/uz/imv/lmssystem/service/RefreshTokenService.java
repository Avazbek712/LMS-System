package uz.imv.lmssystem.service;

import uz.imv.lmssystem.entity.RefreshToken;
import uz.imv.lmssystem.entity.User;

import java.util.Optional;

public interface RefreshTokenService {


    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
