package myecomerce.userservice.application.authService.service;

import java.time.Instant;
import java.util.UUID;
import java.time.Duration;

import myecomerce.userservice.application.annotationCustom.CommandUseCase;
import myecomerce.userservice.application.authService.command.LoginCommand;
import myecomerce.userservice.application.authService.command.LogoutCommand;
import myecomerce.userservice.application.authService.command.RegisterCommand;
import myecomerce.userservice.application.authService.dto.LoginResponse;
import myecomerce.userservice.application.authService.dto.RefreshTokenResponse;
import myecomerce.userservice.application.authService.dto.RegisterResponse;
import myecomerce.userservice.application.authService.exception.InvalidTokenException;
import myecomerce.userservice.application.authService.exception.UnauthorizedException;
import myecomerce.userservice.application.userService.exception.EmailAlreadyExistsException;
import myecomerce.userservice.application.userService.exception.InvalidEmailOrPasswordException;
import myecomerce.userservice.application.userService.exception.UserNotFoundException;
import myecomerce.userservice.domain.model.RefreshToken;
import myecomerce.userservice.domain.model.User;
import myecomerce.userservice.domain.model.UserRole;
import myecomerce.userservice.domain.repository.RefreshTokenRepository;
import myecomerce.userservice.domain.repository.RevokedTokenRepository;
import myecomerce.userservice.domain.repository.UserRepository;

@CommandUseCase
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public AuthServiceImpl(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            RevokedTokenRepository revokedTokenRepository,
            PasswordHasher passwordHasher,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.revokedTokenRepository = revokedTokenRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    @Override
    public RegisterResponse register(RegisterCommand command) {
        userRepository.findByEmail(command.email()).ifPresent(user -> {
            throw new EmailAlreadyExistsException();
        });

        var hashPassword = passwordHasher.hash(command.password());
        User newUser = User.create(command.email(), command.name(), hashPassword);
        User saved = userRepository.save(newUser);

        return new RegisterResponse(
                saved.getId().toString(),
                saved.getEmail(),
                saved.getName(),
                saved.getRole());
    }

    @Override
    public LoginResponse login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new InvalidEmailOrPasswordException());

        boolean matches = passwordHasher.matches(
                command.password(),
                user.getPasswordHash());

        if (!matches) {
            throw new InvalidEmailOrPasswordException();
        }

        String accessToken = tokenService.generateAccessToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole());

        String refreshToken = tokenService.generateRefreshToken(user.getId().toString());

        refreshTokenRepository.save(new RefreshToken(
                UUID.randomUUID(),
                user.getId(),
                refreshToken,
                Instant.now()
                        .plus(Duration.ofDays(30))));

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public LoginResponse loginWithSSO(String idToken) {
        var tokenRevoked = revokedTokenRepository.exists(idToken);
        if (tokenRevoked) {
            throw new UnauthorizedException();
        }
        var userId = UUID.randomUUID();
        String email = tokenService.extractEmailSSO(idToken);
        UserRole userRole = UserRole.USER;
        String accessToken = tokenService.generateAccessToken(
                userId.toString(),
                email,
                userRole);

        String refreshToken = tokenService.generateRefreshToken(userId.toString());

        refreshTokenRepository.save(new RefreshToken(
                UUID.randomUUID(),
                userId,
                refreshToken,
                Instant.now()
                        .plus(Duration.ofDays(30))));

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public RefreshTokenResponse refresh(String refreshToken) {
        RefreshToken stored = refreshTokenRepository
                .findByToken(
                        refreshToken)

                .orElseThrow(
                        InvalidTokenException::new);

        if (stored.expired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidTokenException();
        }

        User user = userRepository
                .findById(
                        stored.getUserId())
                .orElseThrow(
                        UserNotFoundException::new);

        String accessToken = tokenService.generateAccessToken(
                user.getId()
                        .toString(),

                user.getEmail(),
                user.getRole());

        String newRefreshToken = tokenService.generateRefreshToken(user.getId().toString());

        refreshTokenRepository.delete(refreshToken);

        refreshTokenRepository.save(new RefreshToken(

                UUID.randomUUID(),

                user.getId(),

                newRefreshToken,

                Instant.now()
                        .plus(Duration.ofDays(30))));
        ;

        return new RefreshTokenResponse(
                accessToken,
                newRefreshToken);
    }

    @Override
    public void logout(LogoutCommand command) {
        revokedTokenRepository.save(
                command.accessToken());

        refreshTokenRepository.delete(
                command.refreshToken());
    }
}
