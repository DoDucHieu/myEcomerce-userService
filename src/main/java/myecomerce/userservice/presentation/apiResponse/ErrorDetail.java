package myecomerce.userservice.presentation.apiResponse;

public record ErrorDetail (
    String field,
    String message
) {}
