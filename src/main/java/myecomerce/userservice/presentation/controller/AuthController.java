package myecomerce.userservice.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import myecomerce.userservice.application.authService.command.LoginCommand;
import myecomerce.userservice.application.authService.command.RegisterCommand;
import myecomerce.userservice.application.authService.dto.LoginResponse;
import myecomerce.userservice.application.authService.dto.RefreshTokenResponse;
import myecomerce.userservice.application.authService.dto.RegisterResponse;
import myecomerce.userservice.application.authService.service.AuthService;
import myecomerce.userservice.presentation.apiResponse.ApiResponse;
import myecomerce.userservice.presentation.apiResponse.ErrorCode;
import myecomerce.userservice.presentation.apiResponse.RequestContext;
import myecomerce.userservice.presentation.dto.auth.LoginRequest;
import myecomerce.userservice.presentation.dto.auth.RefreshTokenRequest;
import myecomerce.userservice.presentation.dto.auth.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
        
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest req, HttpServletRequest httpRequest) {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        RegisterCommand command = req.toRegisterCommand();
        RegisterResponse result = authService.register(command);
        return ApiResponse.success("User registered successfully", code, requestId, requestId, result, httpRequest.getRequestURI());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        LoginCommand command = request.toLoginCommand();

        LoginResponse res = authService.login(command);

        return ApiResponse.success("Login successfully", code, requestId, requestId, res, httpRequest.getRequestURI());
    }

    @PostMapping("/refresh")
    public ApiResponse<RefreshTokenResponse> refresh(
            @Valid
            @RequestBody
            RefreshTokenRequest request,
            HttpServletRequest httpRequest
    ) {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        RefreshTokenResponse res = authService.refresh(request.refreshToken());
        return ApiResponse.success("Refresh token successfully", code, requestId, requestId, res, httpRequest.getRequestURI());
    }
}
