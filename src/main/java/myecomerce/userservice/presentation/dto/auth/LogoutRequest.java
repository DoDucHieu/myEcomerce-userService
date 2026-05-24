package myecomerce.userservice.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest (
        @NotBlank
        String refreshToken
) {}
