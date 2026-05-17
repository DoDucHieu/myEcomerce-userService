package myecomerce.userservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import myecomerce.userservice.domain.model.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    List<User> findAll(int page, int size, String search);
}
