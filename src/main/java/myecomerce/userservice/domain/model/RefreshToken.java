package myecomerce.userservice.domain.model;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {
    private UUID id;

    private UUID userId;

    private String token;

    private Instant expiresAt;

    public RefreshToken(
            UUID id,
            UUID userId,
            String token,
            Instant expiresAt
    ) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public boolean expired() {
        return Instant.now()
                .isAfter(expiresAt);
    }

    public UUID getId(){
        return id;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
