package myecomerce.userservice.application.userService.command;

public record CreateUserCommand(
    String email,
    String name,
    String passwordHash
) {}
