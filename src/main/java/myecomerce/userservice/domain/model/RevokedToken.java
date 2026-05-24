package myecomerce.userservice.domain.model;

import java.time.Instant;

public class RevokedToken {
    private final String token;

    private final Instant expiresAt;

    public RevokedToken(
            String token,
            Instant expiresAt
    ) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

}
