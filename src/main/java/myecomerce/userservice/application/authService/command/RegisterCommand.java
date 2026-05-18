package myecomerce.userservice.application.authService.command;

public record RegisterCommand (
    String email,
    String password,
    String name
) {}
