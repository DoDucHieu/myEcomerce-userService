package myecomerce.userservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void shouldCreateUserSuccessfully(){
        User user = User.create("test@example.com", "John", "hashedPassword");
        assertNotNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getName());
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty(){
        assertThrows(IllegalArgumentException.class, () -> new User(UUID.randomUUID(), "", "John", "hash", UserRole.USER));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty(){
        assertThrows(IllegalArgumentException.class, () -> User.create("test@example.com", "John", ""));
    }

    @Test
    void shouldUpdateUserSuccessfully(){
        User user = User.create("old@example.com", "OldName", "hash");
        user.update("new@example.com", "NewName");

        assertEquals("new@example.com", user.getEmail());
        assertEquals("NewName", user.getName());
    }
}
