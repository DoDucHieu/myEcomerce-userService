package myecomerce.userservice.application.authService.service;

import java.util.UUID;

import myecomerce.userservice.domain.model.UserRole;

public interface OAuth2TokenService {
    String extractEmail(String token);
    UserRole extractRole(String token);
    UUID extractUserId(String token);
    String extractUserName(String token);
}
