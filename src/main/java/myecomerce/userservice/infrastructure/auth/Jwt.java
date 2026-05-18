package myecomerce.userservice.infrastructure.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import myecomerce.userservice.application.authService.service.TokenService;

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
            String email
    ) {

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(userId)
                .claim("email", email)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();
    }
}
