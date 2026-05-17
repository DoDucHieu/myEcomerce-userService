package myecomerce.userservice.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import myecomerce.userservice.application.userService.command.CreateUserCommand;
import myecomerce.userservice.application.userService.command.UpdateUserCommand;
import myecomerce.userservice.application.userService.dto.CreateUserResponse;
import myecomerce.userservice.application.userService.dto.PaginationResponse;
import myecomerce.userservice.application.userService.dto.UpdateUserResponse;
import myecomerce.userservice.application.userService.dto.UserResponse;
import myecomerce.userservice.application.userService.query.GetUserByIdQuery;
import myecomerce.userservice.application.userService.query.GetUsersQuery;
import myecomerce.userservice.application.userService.service.UserCommandService;
import myecomerce.userservice.application.userService.service.UserQueryService;
import myecomerce.userservice.presentation.apiResponse.ApiResponse;
import myecomerce.userservice.presentation.apiResponse.ErrorCode;
import myecomerce.userservice.presentation.apiResponse.RequestContext;
import myecomerce.userservice.presentation.dto.CreateUserRequest;
import myecomerce.userservice.presentation.dto.GetUsersRequest;
import myecomerce.userservice.presentation.dto.UpdateUserRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateUserResponse> createUser(
        @Valid @RequestBody CreateUserRequest request,
        HttpServletRequest httpRequest) 
    {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        var command = new CreateUserCommand(request.email(), request.name(), request.passwordHash());
        var result = userCommandService.createUser(command);

        return ApiResponse.success("User created successfully", code, requestId, requestId, result, httpRequest.getRequestURI());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UpdateUserResponse> updateUser(
        @Valid @RequestBody UpdateUserRequest req,
        HttpServletRequest httpRequest) 
    {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        var command = new UpdateUserCommand(req.id(), req.email(), req.name()); 
        var result = userCommandService.updateUser(command);
        return ApiResponse.success("User updated successfully", code, requestId, requestId, result, httpRequest.getRequestURI());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PaginationResponse<UserResponse>> getUsers(
        @Valid @ModelAttribute GetUsersRequest request,
        HttpServletRequest httpRequest) 
    {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        GetUsersQuery query = request.toGetUsersQuery();
        PaginationResponse<UserResponse> result = userQueryService.getUsers(query);
        return ApiResponse.success("Users fetched successfully", code, requestId, requestId, result, httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserResponse> getUserById(
        @PathVariable String id,
        HttpServletRequest httpRequest) 
    {
        String requestId = RequestContext.getRequestId();
        String code = ErrorCode.SUCCESS;
        GetUserByIdQuery query = new GetUserByIdQuery(id);
        UserResponse result = userQueryService.getUserById(query);
        return ApiResponse.success("User fetched successfully", code, requestId, requestId, result, httpRequest.getRequestURI());
    }
}