package myecomerce.userservice.infrastructure.persistence.repositoryImpl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import myecomerce.userservice.domain.model.RefreshToken;
import myecomerce.userservice.domain.repository.RefreshTokenRepository;
import myecomerce.userservice.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import myecomerce.userservice.infrastructure.persistence.repository.RefreshTokenJpaRepository;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    public RefreshTokenRepositoryImpl(RefreshTokenJpaRepository refreshTokenJpaRepository) {
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
    }

    private RefreshTokenJpaEntity toEntity(
            RefreshToken token
    ) {

        RefreshTokenJpaEntity entity =
                new RefreshTokenJpaEntity();

        entity.setId(
                token.getId()
        );

        entity.setUserId(
                token.getUserId()
        );

        entity.setToken(
                token.getToken()
        );

        entity.setExpiresAt(
                token.getExpiresAt()
        );

        return entity;
    }

    private RefreshToken toDomain(
            RefreshTokenJpaEntity entity
    ) {

        return new RefreshToken(
                entity.getId(),
                entity.getUserId(),
                entity.getToken(),
                entity.getExpiresAt()
        );
    }


    @Override
    public RefreshToken save(RefreshToken token) {
        return toDomain(refreshTokenJpaRepository.save(toEntity(token)));
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository
        .findByToken(token)
        .map(this::toDomain);
    }

    @Override
    public void delete(String token) {
        refreshTokenJpaRepository.deleteByToken(token);
    }  
}
