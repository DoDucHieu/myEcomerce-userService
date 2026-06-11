package myecomerce.userservice.application.auditService;

import java.time.LocalDateTime;
import java.util.UUID;

import myecomerce.userservice.application.eventPublisherService.EventPublisherCommand;
import myecomerce.userservice.application.eventPublisherService.EventPublisherService;
import myecomerce.userservice.domain.model.AuditLog;

public class AuditServiceImpl implements AuditService {
    public static final String AuditQueueName = "audit.log.queue";
    private final EventPublisherService eventPublisherService;

    public AuditServiceImpl(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
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
            
        eventPublisherService.publish(new EventPublisherCommand<AuditLog>(AuditQueueName, auditLog));
    }
    
}
