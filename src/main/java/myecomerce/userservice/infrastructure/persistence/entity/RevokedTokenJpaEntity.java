package myecomerce.userservice.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "revoked_tokens"
)

public class RevokedTokenJpaEntity {

    @Id
    private String token;

    private Instant expiresAt;

    protected RevokedTokenJpaEntity() {}

    public RevokedTokenJpaEntity(
            String token,
            Instant expiresAt
    ) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

}