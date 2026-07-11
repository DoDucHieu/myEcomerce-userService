package myecomerce.userservice.application.userService.service;

import myecomerce.userservice.application.userService.command.CreateUserCommand;
import myecomerce.userservice.application.userService.command.UpdateUserCommand;
import myecomerce.userservice.application.userService.dto.CreateUserResponse;
import myecomerce.userservice.application.userService.dto.UpdateUserResponse;

public interface UserCommandService {
    CreateUserResponse createUser(CreateUserCommand req);
    
    UpdateUserResponse updateUser(UpdateUserCommand req);

    void deleteUser(String id);
}
