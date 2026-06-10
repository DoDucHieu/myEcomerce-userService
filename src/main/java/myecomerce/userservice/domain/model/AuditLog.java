package myecomerce.userservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLog {
    private UUID id;

    private String userId;

    private String action;

    private String method;

    private String path;

    private String ip;

    private Integer status;

    private Long duration;

    private LocalDateTime createdAt;

    public AuditLog(UUID id, String userId, String action, String method, String path, String ip, Integer status, Long duration, LocalDateTime createdAt){
        this.id = id;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
