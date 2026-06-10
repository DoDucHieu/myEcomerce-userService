package myecomerce.userservice.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    private String action;

    private String method;

    private String path;

    private String ip;

    private Integer status;

    private Long duration;

    private LocalDateTime createdAt;

    public AuditJpaEntity(String userId, String action, String method, String path, String ip, Integer status, Long duration, LocalDateTime createdAt) {
        this.userId = userId;
        this.action = action;
        this.method = method;
        this.path = path;
        this.ip = ip;
        this.status = status;
        this.duration = duration;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getIp() {
        return ip;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getDuration() {
        return duration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
