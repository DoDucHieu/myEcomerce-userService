package myecomerce.userservice.application.userService.dto;

import java.util.UUID;

import myecomerce.userservice.domain.model.UserRole;

public record CreateUserResponse(
    UUID id,
    String email,
    String name,
    UserRole role
) {}
