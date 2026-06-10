package myecomerce.userservice.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import myecomerce.userservice.infrastructure.persistence.entity.AuditJpaEntity;

public interface AuditJpaRepository extends JpaRepository<AuditJpaEntity, UUID> {
    
}
