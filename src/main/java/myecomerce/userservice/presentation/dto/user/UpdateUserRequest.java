package myecomerce.userservice.presentation.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    String email,

    @NotBlank(message = "Name is required")
    String name
) {}
