package myecomerce.userservice.application.authService.command;

public record LogoutCommand(
    String accessToken,
    String refreshToken
) {
}
