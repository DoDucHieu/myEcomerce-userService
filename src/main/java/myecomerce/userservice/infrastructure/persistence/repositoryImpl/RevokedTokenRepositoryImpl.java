package myecomerce.userservice.infrastructure.persistence.repositoryImpl;

import org.springframework.stereotype.Component;

import myecomerce.userservice.application.authService.service.TokenService;
import myecomerce.userservice.domain.repository.RevokedTokenRepository;
import myecomerce.userservice.infrastructure.persistence.entity.RevokedTokenJpaEntity;
import myecomerce.userservice.infrastructure.persistence.repository.RevokedTokenJpaRepository;

@Component
public class RevokedTokenRepositoryImpl implements RevokedTokenRepository{
    private final RevokedTokenJpaRepository revokedTokenJpaRepository;
    private final TokenService tokenService;

    public RevokedTokenRepositoryImpl(RevokedTokenJpaRepository revokedTokenJpaRepository, TokenService tokenService) {
        this.revokedTokenJpaRepository = revokedTokenJpaRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void save(String token) {
        revokedTokenJpaRepository.save(
                new RevokedTokenJpaEntity(
                        token,
                        tokenService.extractExpiration(
                                token
                        )
                )
        );
    }

    @Override
    public boolean exists(String token) {
        return revokedTokenJpaRepository.existsById(
                token
        );
    }
    
}
