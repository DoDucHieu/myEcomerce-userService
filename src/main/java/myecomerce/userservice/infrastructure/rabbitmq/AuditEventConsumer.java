package myecomerce.userservice.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import myecomerce.userservice.application.auditService.AuditServiceImpl;
import myecomerce.userservice.domain.model.AuditLog;
import myecomerce.userservice.domain.repository.AuditLogRepository;

@Component
public class AuditEventConsumer {
    private final AuditLogRepository auditLogRepository;

    public AuditEventConsumer(
            AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @RabbitListener(queues = AuditServiceImpl.AuditQueueName)
    public void consume(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}
