package myecomerce.userservice.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import myecomerce.userservice.infrastructure.persistence.entity.RevokedTokenJpaEntity;

public interface RevokedTokenJpaRepository extends JpaRepository<RevokedTokenJpaEntity, String>{}
