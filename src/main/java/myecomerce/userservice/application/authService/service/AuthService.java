package myecomerce.userservice.application.authService.service;

import myecomerce.userservice.application.authService.command.LoginCommand;
import myecomerce.userservice.application.authService.command.RegisterCommand;
import myecomerce.userservice.application.authService.dto.LoginResponse;
import myecomerce.userservice.application.authService.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterCommand command);
    LoginResponse login(LoginCommand command);
}
