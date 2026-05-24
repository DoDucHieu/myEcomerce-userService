package myecomerce.userservice.application.authService.dto;

public record RefreshTokenResponse(
    String accessToken,
    String refreshToken
) {
}
