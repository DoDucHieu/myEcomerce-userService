package myecomerce.userservice.domain.model;

import java.util.UUID;

public class User {

    private UUID id;
    private String email;
    private String name;
    private String passwordHash;
    private UserRole role;

    public User(UUID id, String email, String name, String passwordHash, UserRole role) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        this.id = id;
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public static User create(String email, String name, String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash is required");
        }

        return new User(UUID.randomUUID(), email, name, passwordHash, UserRole.USER);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole(){
        return this.role;
    }

    public void setRole(UserRole role){
        this.role = role;
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
