package myecomerce.userservice.domain.repository;

import java.util.Optional;

import myecomerce.userservice.domain.model.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    void delete(String token);
}
