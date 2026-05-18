package myecomerce.userservice.application.authService.service;

import myecomerce.userservice.application.authService.command.LoginCommand;
import myecomerce.userservice.application.authService.command.RegisterCommand;
import myecomerce.userservice.application.authService.dto.LoginResponse;
import myecomerce.userservice.application.authService.dto.RegisterResponse;
import myecomerce.userservice.application.userService.exception.EmailAlreadyExistsException;
import myecomerce.userservice.application.userService.exception.InvalidEmailOrPasswordException;
import myecomerce.userservice.domain.model.User;
import myecomerce.userservice.domain.repository.UserRepository;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public AuthServiceImpl(UserRepository userRepository, PasswordHasher passwordHasher, TokenService tokenService) {
        this.userRepository = userRepository;
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
                saved.getName());
    }

    @Override
    public LoginResponse login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
        .orElseThrow(() -> new InvalidEmailOrPasswordException());

        boolean matches = passwordHasher.matches(
            command.password(),
            user.getPasswordHash()
        );

        if (!matches) {
            throw new InvalidEmailOrPasswordException();
        }

        String accessToken = tokenService.generateAccessToken(
            user.getId().toString(),
            user.getEmail()
        );

        return new LoginResponse(accessToken);
    }
}
