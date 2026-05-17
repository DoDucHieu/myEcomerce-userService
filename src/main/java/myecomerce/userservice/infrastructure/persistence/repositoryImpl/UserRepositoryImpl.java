package myecomerce.userservice.infrastructure.persistence.repositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import myecomerce.userservice.domain.model.User;
import myecomerce.userservice.domain.repository.UserRepository;
import myecomerce.userservice.infrastructure.persistence.entity.UserJpaEntity;
import myecomerce.userservice.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    private User toDomain(UserJpaEntity e) {
        return new User(e.getId(), e.getEmail(), e.getName(), e.getPasswordHash());
    }

    @Override
    public User save(User user) {
        UUID userId = user.getId() != null ? user.getId() : UUID.randomUUID();
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(userId);
        entity.setEmail(user.getEmail());
        entity.setName(user.getName());
        entity.setPasswordHash(user.getPasswordHash());
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<UserJpaEntity> result;

        if (search != null && !search.isBlank()) {
            result = jpaRepository.findByEmailContainingIgnoreCase(search, pageable);
        } else { 
            result = jpaRepository.findAll(pageable);
        }

        List<User> users = result.getContent()
                .stream()
                .map(this::toDomain)
                .toList();

        return users;
    }
}
