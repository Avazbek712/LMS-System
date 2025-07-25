package uz.imv.lmssystem.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Created by Avazbek on 19/06/25 14:19
 */
@SuppressWarnings("ALL")
@Service
public class JwtService {
    private final String ACCESS_TOKEN_SECRET_KEY = "gX9!vB3$kLpZ7#tQr@5mJw^N2sDfUeY0cHaRbMiT1xVz";

    private final String REFRESH_TOKEN_SECRET_KEY = "aK8#jN5$sLqW9#eRt@3pYx^D1cHgVbMfI6uOz^P4sT2";

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 минут
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 дней


    private Key getSigningKey(String token) {
        return Keys.hmacShaKeyFor(token.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(ACCESS_TOKEN_SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey(REFRESH_TOKEN_SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsernameFromAccessToken(String token) {
        return extractClaim(token, Claims::getSubject, ACCESS_TOKEN_SECRET_KEY);
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractClaim(token, Claims::getSubject, REFRESH_TOKEN_SECRET_KEY);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromAccessToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, ACCESS_TOKEN_SECRET_KEY);
    }

    // ВАЖНО: Мы не проверяем Refresh Token на истечение здесь. Это сделает сервис.
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromRefreshToken(token);
        return (username.equals(userDetails.getUsername()));
    }

    private boolean isTokenExpired(String token, String secret) {
        return extractExpiration(token, secret).before(new Date());
    }

    private Date extractExpiration(String token, String secret) {
        return extractClaim(token, Claims::getExpiration, secret);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        final Claims claims = extractAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
