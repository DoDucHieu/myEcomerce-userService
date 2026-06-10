package myecomerce.userservice.application.auditService;

import java.time.LocalDateTime;
import java.util.UUID;

import myecomerce.userservice.domain.model.AuditLog;
import myecomerce.userservice.domain.repository.AuditLogRepository;

public class AuditServiceImpl implements AuditService {
    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void log(AuditEventCommand command) {
        AuditLog auditLog = new AuditLog(
            UUID.randomUUID(),
            command.userId(), 
            command.action(), 
            command.method(), 
            command.path(), 
            command.ip(), 
            command.status(), 
            command.duration(),
            LocalDateTime.now());
            
        auditLogRepository.save(auditLog);
    }
    
}
