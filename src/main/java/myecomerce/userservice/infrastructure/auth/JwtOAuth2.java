package myecomerce.userservice.infrastructure.auth;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.jwt.Jwt;
import myecomerce.userservice.application.authService.service.OAuth2TokenService;
import myecomerce.userservice.domain.model.UserRole;

@Component
public class JwtOAuth2 implements OAuth2TokenService{
    private final JwtDecoder jwtDecoder;

    public JwtOAuth2(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public String extractEmail(String token) {


        Jwt jwt = jwtDecoder.decode(token);

        return jwt.getClaimAsString("email");
    }

    @Override
    public UserRole extractRole(String token) {
        throw new UnsupportedOperationException("Unimplemented method 'extractRole'");
    }
    
}
