package myecomerce.userservice.application.authService.service;

import java.time.Instant;

public interface TokenService {
    String generateAccessToken(String userId, String email);
    String generateRefreshToken(String userId);
    boolean validate(String token);
    String extractUserId(String token);
    public Instant extractExpiration(String token);
}
