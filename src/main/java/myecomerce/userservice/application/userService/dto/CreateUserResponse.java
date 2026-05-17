package myecomerce.userservice.application.userService.dto;

import java.util.UUID;

public record CreateUserResponse(
    UUID id,
    String email,
    String name
) {}
