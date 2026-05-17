package myecomerce.userservice.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import myecomerce.userservice.application.userService.dto.CreateUserResponse;
import myecomerce.userservice.application.userService.dto.UpdateUserResponse;
import myecomerce.userservice.application.userService.dto.UserResponse;
import myecomerce.userservice.application.userService.mapper.UserMapper;
import myecomerce.userservice.domain.model.User;

@Mapper(componentModel = "spring")
public interface IUserMapper extends UserMapper{
    @Override
    CreateUserResponse toCreateUserResponse(User user);

    @Override
    UpdateUserResponse toUpdateUserResponse(User user);

    @Override
    @Mapping(target = "id", expression = "java(user.getId().toString())")
    UserResponse toUserResponse(User user);
}
