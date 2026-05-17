package myecomerce.userservice.application.userService.mapper;

import myecomerce.userservice.application.userService.dto.CreateUserResponse;
import myecomerce.userservice.application.userService.dto.UpdateUserResponse;
import myecomerce.userservice.application.userService.dto.UserResponse;
import myecomerce.userservice.domain.model.User;

public interface UserMapper {
    CreateUserResponse toCreateUserResponse(User user);

    UpdateUserResponse toUpdateUserResponse(User user);

    UserResponse toUserResponse(User user);
}
