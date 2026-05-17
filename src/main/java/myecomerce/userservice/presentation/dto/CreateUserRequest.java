package myecomerce.userservice.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    String email,
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Password hash is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password hash must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    String passwordHash
) {}