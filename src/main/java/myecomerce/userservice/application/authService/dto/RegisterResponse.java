package myecomerce.userservice.application.authService.dto;

import myecomerce.userservice.domain.model.UserRole;

public record RegisterResponse(
    String id,
    String email,
    String name,
    UserRole role
) {
}