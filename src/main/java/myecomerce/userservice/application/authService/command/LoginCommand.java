package myecomerce.userservice.application.authService.command;

public record LoginCommand (
    String email,
    String password
){}
