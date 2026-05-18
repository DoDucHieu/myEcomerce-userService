package myecomerce.userservice.application.authService.service;

public interface TokenService {
    String generateAccessToken(String userId, String email);
}
