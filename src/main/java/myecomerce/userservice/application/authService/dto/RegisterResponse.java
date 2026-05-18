package myecomerce.userservice.application.authService.dto;

public record RegisterResponse(
    String id,
    String email,
    String name
) {
}