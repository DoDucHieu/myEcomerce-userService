package myecomerce.userservice.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import myecomerce.userservice.application.auditService.AuditServiceImpl;
import myecomerce.userservice.application.eventPublisherService.EventPublisherCommand;
import myecomerce.userservice.domain.model.AuditLog;
import myecomerce.userservice.domain.repository.AuditLogRepository;

public class AuditEventConsumer {
    private final AuditLogRepository auditLogRepository;

    public AuditEventConsumer(
            AuditLogRepository auditLogRepository
    ) {
        this.auditLogRepository = auditLogRepository;
    }

    @RabbitListener(
            queues = AuditServiceImpl.AuditQueueName
    )
    public void consume(
            EventPublisherCommand<AuditLog> command
    ) {

        auditLogRepository.save(
                command.payload()
        );
    }
}
