package myecomerce.userservice.domain.repository;

import myecomerce.userservice.domain.model.AuditLog;

public interface AuditLogRepository {
    AuditLog save(AuditLog log);
}
