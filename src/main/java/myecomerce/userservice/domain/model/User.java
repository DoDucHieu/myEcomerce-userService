package myecomerce.userservice.domain.model;

import java.util.UUID;

public class User {

    private UUID id;
    private String email;
    private String name;

    public User(UUID id, String email, String name) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static User create(String email, String name) {
        return new User(UUID.randomUUID(), email, name);
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User update(String email, String name) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
    
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    
        this.email = email;
        this.name = name;
        return this;
    }
}
