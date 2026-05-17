package myecomerce.userservice.application.userService.service;

import java.util.UUID;
import myecomerce.userservice.application.userService.command.CreateUserCommand;
import myecomerce.userservice.application.userService.command.UpdateUserCommand;
import myecomerce.userservice.application.userService.dto.CreateUserResponse;
import myecomerce.userservice.application.userService.dto.UpdateUserResponse;
import myecomerce.userservice.application.userService.exception.EmailAlreadyExistsException;
import myecomerce.userservice.application.userService.exception.UserNotFoundException;
import myecomerce.userservice.application.userService.mapper.UserMapper;
import myecomerce.userservice.domain.model.User;
import myecomerce.userservice.domain.repository.UserRepository;

public class UserCommandServiceImpl implements UserCommandService {
 
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserCommandServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public CreateUserResponse createUser(CreateUserCommand req) {
        userRepository.findByEmail(req.email()).ifPresent(user -> {
            throw new EmailAlreadyExistsException();
        });

        User newUser = User.create(req.email(), req.name(), req.passwordHash());
        User saved = userRepository.save(newUser);
        return new CreateUserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getName());
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserCommand req) {
        User user = userRepository.findById(UUID.fromString(req.id()))
                .orElseThrow(UserNotFoundException::new);

        userRepository.findByEmail(req.email())
                .filter(u -> !u.getId().equals(user.getId()))
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException();
                });

        user.update(req.email(), req.name());

        User updatedUser = userRepository.save(user);

        return userMapper.toUpdateUserResponse(updatedUser);
    }
}
