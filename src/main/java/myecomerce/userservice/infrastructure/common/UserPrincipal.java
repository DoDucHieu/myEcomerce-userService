package myecomerce.userservice.infrastructure.common;

import java.util.UUID;

import myecomerce.userservice.domain.model.UserRole;

public record UserPrincipal(
    UUID id,
    String email,
    UserRole role
) {}
