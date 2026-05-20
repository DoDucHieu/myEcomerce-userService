package myecomerce.userservice.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

    @NotBlank
    String refreshToken

) {
}
