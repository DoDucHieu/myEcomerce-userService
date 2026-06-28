package myecomerce.userservice.application.authService.service;

import myecomerce.userservice.domain.model.UserRole;

public interface OAuth2TokenService {
    String extractEmail(String token);
    UserRole extractRole(String token);
}
