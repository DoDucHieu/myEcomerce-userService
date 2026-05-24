package myecomerce.userservice.domain.repository;

public interface RevokedTokenRepository {
    void save(String token);

    boolean exists(String token);
}
