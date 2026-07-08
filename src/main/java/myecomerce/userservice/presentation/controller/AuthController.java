package myecomerce.userservice.presentation.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import myecomerce.userservice.application.authService.command.LoginCommand;
import myecomerce.userservice.application.authService.command.LogoutCommand;
import myecomerce.userservice.application.authService.command.RegisterCommand;
import myecomerce.userservice.application.authService.dto.LoginResponse;
import myecomerce.userservice.application.authService.dto.RefreshTokenResponse;
import myecomerce.userservice.application.authService.dto.RegisterResponse;
import myecomerce.userservice.application.authService.service.AuthService;
import myecomerce.userservice.infrastructure.config.KeycloakProperties;
import myecomerce.userservice.presentation.apiResponse.ApiResponse;
import myecomerce.userservice.presentation.apiResponse.ErrorCode;
import myecomerce.userservice.presentation.apiResponse.RequestContext;
import myecomerce.userservice.presentation.dto.auth.LoginRequest;
import myecomerce.userservice.presentation.dto.auth.LogoutRequest;
import myecomerce.userservice.presentation.dto.auth.RefreshTokenRequest;
import myecomerce.userservice.presentation.dto.auth.RegisterRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {
        private final AuthService authService;
        private final KeycloakProperties keycloakProperties;

        public AuthController(AuthService authService, KeycloakProperties keycloakProperties) {
                this.authService = authService;
                this.keycloakProperties = keycloakProperties;
        }

        @PostMapping("/register")
        @ResponseStatus(HttpStatus.CREATED)
        public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest req,
                        HttpServletRequest httpRequest) {
                String requestId = RequestContext.getRequestId();
                String code = ErrorCode.SUCCESS;
                RegisterCommand command = req.toRegisterCommand();
                RegisterResponse result = authService.register(command);
                return ApiResponse.success("User registered successfully", code, requestId, requestId, result,
                                httpRequest.getRequestURI());
        }

        @PostMapping("/login")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                        HttpServletRequest httpRequest) {
                String requestId = RequestContext.getRequestId();
                String code = ErrorCode.SUCCESS;
                LoginCommand command = request.toLoginCommand();

                LoginResponse res = authService.login(command);

                return ApiResponse.success("Login successfully", code, requestId, requestId, res,
                                httpRequest.getRequestURI());
        }

        @PostMapping("/refresh")
        public ApiResponse<RefreshTokenResponse> refresh(
                        @Valid @RequestBody RefreshTokenRequest request,
                        HttpServletRequest httpRequest) {
                String requestId = RequestContext.getRequestId();
                String code = ErrorCode.SUCCESS;
                RefreshTokenResponse res = authService.refresh(request.refreshToken());
                return ApiResponse.success("Refresh token successfully", code, requestId, requestId, res,
                                httpRequest.getRequestURI());
        }

        @PostMapping("/logout")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ApiResponse<Boolean> logout(
                        @RequestHeader("Authorization") String authorization,

                        @RequestBody LogoutRequest req,
                        HttpServletRequest httpRequest) {
                String requestId = RequestContext.getRequestId();
                String code = ErrorCode.SUCCESS;

                String accessToken = authorization
                                .replace(
                                                "Bearer ",
                                                "");

                authService.logout(

                                new LogoutCommand(

                                                accessToken,

                                                req.refreshToken()

                                )

                );

                return ApiResponse.success("Revoked token successfully", code, requestId, requestId, true,
                                httpRequest.getRequestURI());
        }

        @GetMapping("/oidc-callback")
        public void oidcCallback(
                        @RequestParam("code") String authCode,
                        HttpServletRequest httpRequest,
                        HttpServletResponse httpResponse) throws IOException {
                System.out.println("=== STEP 1 ===");
                String tokenUrl = keycloakProperties.getServerUrl()
                                + "/realms/"
                                + keycloakProperties.getRealm()
                                + "/protocol/openid-connect/token";
                System.out.println("=== STEP 2 ===");
                RestTemplate restTemplate = new RestTemplate();

                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("code", authCode);
                formData.add("grant_type", keycloakProperties.getGrantType());
                formData.add("client_id", keycloakProperties.getClientId());
                formData.add("client_secret", keycloakProperties.getClientSecret());
                formData.add("redirect_uri", keycloakProperties.getRedirectUri());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

                try {
                        System.out.println("=== STEP 3 ===");
                        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

                        Map<String, Object> tokens = response.getBody();

                        String accessTokenSSO = (String) tokens.get("access_token");
                        String refreshTokenSSO = (String) tokens.get("refresh_token");
                        String idToken = (String) tokens.get("id_token");
                        System.out.println("=== ID TOKEN: " + idToken);

                        var loginWithSSOResponse = authService.loginWithSSO(idToken);

                        var appAccessToken = loginWithSSOResponse.accessToken();
                        var appRefreshToken = loginWithSSOResponse.refreshToken();

                        ResponseCookie accessCookie = ResponseCookie.from("access_token", appAccessToken)
                                        .httpOnly(true)
                                        .secure(false) // localhost is false, production is true
                                        .path("/")
                                        .maxAge(Duration.ofMinutes(15))
                                        .sameSite("Lax")
                                        .build();

                        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", appRefreshToken)
                                        .httpOnly(true)
                                        .secure(false)
                                        .path("/auth/refresh")
                                        .maxAge(Duration.ofDays(7))
                                        .sameSite("Lax")
                                        .build();

                        httpResponse.addHeader(
                                        HttpHeaders.SET_COOKIE,
                                        accessCookie.toString());

                        httpResponse.addHeader(
                                        HttpHeaders.SET_COOKIE,
                                        refreshCookie.toString());

                        String requestId = RequestContext.getRequestId();
                        String code = ErrorCode.SUCCESS;

                        httpResponse.sendRedirect("http://localhost:3000");
                } catch (Exception ex) {
                        System.out.println("=================================");
                        System.out.println("Exception class: " + ex.getClass().getName());
                        System.out.println("Message: " + ex.getMessage());

                        Throwable t = ex;
                        while (t != null) {
                                System.out.println("CAUSE: " + t.getClass().getName());
                                System.out.println("MESSAGE: " + t.getMessage());
                                t = t.getCause();
                        }

                        throw ex;
                }
        }

}
