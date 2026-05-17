package myecomerce.userservice.application.userService.service;

import myecomerce.userservice.application.userService.dto.PaginationResponse;
import myecomerce.userservice.application.userService.dto.UserResponse;
import myecomerce.userservice.application.userService.query.GetUserByIdQuery;
import myecomerce.userservice.application.userService.query.GetUsersQuery;

public interface UserQueryService {
    UserResponse getUserById(GetUserByIdQuery query);

    PaginationResponse<UserResponse> getUsers(GetUsersQuery query);
}
