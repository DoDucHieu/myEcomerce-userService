package myecomerce.userservice.application.common;

import java.util.UUID;

import myecomerce.userservice.domain.model.UserRole;

public interface ICurrentUser {
    UUID getId();
    String getEmail();
    UserRole getRole();
}
