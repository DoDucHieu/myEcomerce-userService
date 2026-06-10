package myecomerce.userservice.application.auditService;

public record AuditEventCommand 
(
    String userId,
    String action,
    String method,
    String path,
    String ip,
    Integer status,
    Long duration
)
{}
