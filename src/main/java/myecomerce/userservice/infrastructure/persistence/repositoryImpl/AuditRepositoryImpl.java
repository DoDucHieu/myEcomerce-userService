package myecomerce.userservice.infrastructure.persistence.repositoryImpl;

import org.springframework.stereotype.Component;

import myecomerce.userservice.domain.model.AuditLog;
import myecomerce.userservice.domain.repository.AuditLogRepository;
import myecomerce.userservice.infrastructure.persistence.entity.AuditJpaEntity;
import myecomerce.userservice.infrastructure.persistence.repository.AuditJpaRepository;

@Component
public class AuditRepositoryImpl implements AuditLogRepository{
    private final AuditJpaRepository auditJpaRepository;

    public AuditRepositoryImpl(AuditJpaRepository auditJpaRepository) {
        this.auditJpaRepository = auditJpaRepository;
    }

    private AuditLog toDomain(AuditJpaEntity entity) {
        return new AuditLog(
            entity.getId(), 
            entity.getUserId(), 
            entity.getAction(), 
            entity.getMethod(), 
            entity.getPath(), 
            entity.getIp(), 
            entity.getStatus(), 
            entity.getDuration(),
            entity.getCreatedAt());
    }

    private AuditJpaEntity toEntity(AuditLog log) {
        return new AuditJpaEntity(
            log.getUserId(), 
            log.getAction(), 
            log.getMethod(), 
            log.getPath(), 
            log.getIp(), 
            log.getStatus(), 
            log.getDuration(),
            log.getCreatedAt()
        );
    }

    @Override
    public AuditLog save(AuditLog log) {
        return toDomain(auditJpaRepository.save(toEntity(log)));
    }
}
