package myecomerce.userservice.application.userService.service;

import java.util.UUID;

import myecomerce.userservice.application.common.ICurrentUser;
import myecomerce.userservice.application.userService.dto.GetMeResponse;
import myecomerce.userservice.application.userService.dto.PaginationResponse;
import myecomerce.userservice.application.userService.dto.UserResponse;
import myecomerce.userservice.application.userService.exception.UserNotFoundException;
import myecomerce.userservice.application.userService.mapper.UserMapper;
import myecomerce.userservice.application.userService.query.GetUserByIdQuery;
import myecomerce.userservice.application.userService.query.GetUsersQuery;
import myecomerce.userservice.domain.model.User;
import myecomerce.userservice.domain.repository.UserRepository;

public class UserQueryServiceImpl implements UserQueryService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ICurrentUser currentUser;
    
    public UserQueryServiceImpl(UserRepository userRepository, UserMapper userMapper, ICurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.currentUser = currentUser;
    }

    
    @Override
    public UserResponse getUserById(GetUserByIdQuery query) {
        User user = userRepository.findById(UUID.fromString(query.id()))
                .orElseThrow(UserNotFoundException::new);

        return userMapper.toUserResponse(user);
    }

    @Override
    public PaginationResponse<UserResponse> getUsers(GetUsersQuery query) {
        var pagination = query.paginationQuery();
        var users = userRepository.findAll(
                pagination.page(),
                pagination.size(),
                pagination.search()
        );

        var data = users
                .stream()
                .map(userMapper::toUserResponse)
                .toList();

        return new PaginationResponse<>(
                data,
                pagination.page(),
                pagination.size(),
                data.size()
        );
    }


    @Override
    public GetMeResponse getMe() {
        UUID userId = currentUser.getId();
        User user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);

        return userMapper.toGetMeResponse(user);
    }
    
}
