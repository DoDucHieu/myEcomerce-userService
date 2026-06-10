package myecomerce.userservice.application.auditService;

public interface AuditService {
    void log(AuditEventCommand command);
}
