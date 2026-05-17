package myecomerce.userservice.application.userService.command;

public record UpdateUserCommand(
    String id,
    String email,
    String name
) {}