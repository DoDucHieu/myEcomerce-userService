package myecomerce.userservice.application.authService.service;

public interface TokenService {
    String generateAccessToken(String userId, String email);
    String generateRefreshToken(String userId);
    boolean validate(String token);
    String extractUserId(String token);
}
