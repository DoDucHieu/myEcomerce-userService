package myecomerce.userservice.infrastructure.auth;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;
import myecomerce.userservice.application.authService.service.OAuth2TokenService;
import myecomerce.userservice.domain.model.UserRole;

// @Component
// public class JwtOAuth2 implements OAuth2TokenService {
//     private final JwtDecoder jwtDecoder;

//     public JwtOAuth2(JwtDecoder jwtDecoder) {
//         this.jwtDecoder = jwtDecoder;
//     }

//     @Override
//     public String extractEmail(String token) {

//         Jwt jwt = jwtDecoder.decode(token);

//         return jwt.getClaimAsString("email");
//     }

//     @Override
//     public UserRole extractRole(String token) {
//         throw new UnsupportedOperationException("Unimplemented method 'extractRole'");
//     }

//     @Override
//     public UUID extractUserId(String token) {
//         Jwt jwt = jwtDecoder.decode(token);

//         return UUID.fromString(jwt.getClaimAsString("sub"));
//     }

//     @Override
//     public String extractUserName(String token) {
//         Jwt jwt = jwtDecoder.decode(token);

//         return jwt.getClaimAsString("name");
//     }

// }


@Component
public class JwtOAuth2 implements OAuth2TokenService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private JsonNode claims(String token) {

        try {

            String payload = token.split("\\.")[1];

            String json = new String(
                    Base64.getUrlDecoder().decode(payload),
                    StandardCharsets.UTF_8);

            return objectMapper.readTree(json);

        } catch (Exception ex) {
            throw new RuntimeException("Invalid ID Token", ex);
        }
    }

    @Override
    public UUID extractUserId(String token) {

        return UUID.fromString(
                claims(token).get("sub").asText());
    }

    @Override
    public String extractEmail(String token) {

        return claims(token)
                .get("email")
                .asText();
    }

    @Override
    public String extractUserName(String token) {

        return claims(token)
                .get("preferred_username")
                .asText();
    }

    @Override
    public UserRole extractRole(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractRole'");
    }
}
