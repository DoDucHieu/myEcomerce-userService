package myecomerce.userservice.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import myecomerce.userservice.infrastructure.persistence.entity.RefreshTokenJpaEntity;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity,UUID> {
    Optional<RefreshTokenJpaEntity>findByToken(String token);

    void deleteByToken(String token);
}
