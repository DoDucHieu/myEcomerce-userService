package myecomerce.userservice.application.userService.dto;

import java.util.UUID;

public record UpdateUserResponse(
        UUID id,
        String email,
        String name
) {}