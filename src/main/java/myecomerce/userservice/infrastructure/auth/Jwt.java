package myecomerce.userservice.infrastructure.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import myecomerce.userservice.application.authService.service.TokenService;
import myecomerce.userservice.domain.model.UserRole;

@Component
public class Jwt implements TokenService{
    private final SecretKey secretKey;

    public Jwt(
            @Value("${jwt.secret}") String secret
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateAccessToken(
            String userId,
            String email,
            UserRole role
    ) { 

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(userId)
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean validate(String token) {
        try {
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token);

                return true;
        } catch (Exception e) {
                return false;
        }
    }

    @Override
    public String extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String generateRefreshToken(String userId) {
        return Jwts.builder()

        .subject(userId)

        .expiration(
                new Date(
                        System.currentTimeMillis()
                                + 30L
                                * 24
                                * 60
                                * 60
                                * 1000
                )
        )

        .signWith(secretKey)

        .compact();
    }

    @Override
    public Instant extractExpiration(String token) {
        Date exp =
            Jwts.parser()
                    .verifyWith(
                            secretKey
                    )
                    .build()
                    .parseSignedClaims(
                            token
                    )
                    .getPayload()
                    .getExpiration();

        return exp.toInstant();
    }

    @Override
    public UserRole extractRole(String token) {
        String role = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("role", String.class);
        return UserRole.valueOf(role);
    }

    @Override
    public String extractEmail(String token) {
        String email = Jwts.parser()
            .verifyWith(secretKey)
            .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
        return email;
    }
}
