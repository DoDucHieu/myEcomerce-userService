package myecomerce.userservice.application.authService.service;

import java.time.Instant;

import myecomerce.userservice.domain.model.UserRole;

public interface TokenService {
    String generateAccessToken(String userId, String email, UserRole role);
    String generateRefreshToken(String userId);
    boolean validate(String token);
    String extractUserId(String token);
    String extractEmail(String token);
    UserRole extractRole(String token);
    public Instant extractExpiration(String token);
}
