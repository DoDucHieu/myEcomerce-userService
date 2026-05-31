package myecomerce.userservice.application.userService.dto;

import myecomerce.userservice.domain.model.UserRole;

public record GetMeResponse(
    String id,
    String email,
    String name,
    UserRole role
){}
