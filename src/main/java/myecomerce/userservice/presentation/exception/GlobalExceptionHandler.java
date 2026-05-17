package myecomerce.userservice.presentation.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import myecomerce.userservice.application.userService.exception.EmailAlreadyExistsException;
import myecomerce.userservice.application.userService.exception.UserNotFoundException;
import myecomerce.userservice.presentation.apiResponse.ApiResponse;
import myecomerce.userservice.presentation.apiResponse.ErrorCode;
import myecomerce.userservice.presentation.apiResponse.ErrorDetail;
import myecomerce.userservice.presentation.apiResponse.RequestContext;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Validate error from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String requestId = RequestContext.getRequestId();
        List<ErrorDetail> errors = ex.getBindingResult()
                                    .getFieldErrors()
                                    .stream()
                                    .map(error -> new ErrorDetail(
                                        error.getField(),
                                        error.getDefaultMessage()
                                    ))
                                    .toList();

        return ApiResponse.error("Validation failed", ErrorCode.VALIDATION_FAILED, requestId, requestId, errors, req.getRequestURI());
    }

    // User not found
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFound(UserNotFoundException ex, HttpServletRequest req) {
        String requestId = RequestContext.getRequestId();
        List<ErrorDetail> errors = List.of(new ErrorDetail("user", ex.getMessage()));
        return ApiResponse.error(ex.getMessage(), ErrorCode.USER_NOT_FOUND, requestId, requestId, errors, req.getRequestURI());
    }

    // Email duplicate
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleEmailExist(EmailAlreadyExistsException ex, HttpServletRequest req) {
        String requestId = RequestContext.getRequestId();
        List<ErrorDetail> errors = List.of(new ErrorDetail("email", ex.getMessage()));
        return ApiResponse.error(ex.getMessage(), ErrorCode.EMAIL_ALREADY_EXISTS, requestId, requestId, errors, req.getRequestURI());
    }

    // fallback
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(Exception ex, HttpServletRequest req) {
        String requestId = RequestContext.getRequestId();
        List<ErrorDetail> errors = List.of(new ErrorDetail("error", "Internal server error"));
        return ApiResponse.error("Internal server error", ErrorCode.INTERNAL_SERVER_ERROR, requestId, requestId, errors, req.getRequestURI());
    }
}
