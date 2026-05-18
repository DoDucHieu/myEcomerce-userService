package myecomerce.userservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myecomerce.userservice.application.authService.service.AuthService;
import myecomerce.userservice.application.authService.service.AuthServiceImpl;
import myecomerce.userservice.application.authService.service.PasswordHasher;
import myecomerce.userservice.application.authService.service.TokenService;
import myecomerce.userservice.application.userService.service.UserCommandService;
import myecomerce.userservice.application.userService.service.UserCommandServiceImpl;
import myecomerce.userservice.application.userService.service.UserQueryService;
import myecomerce.userservice.application.userService.service.UserQueryServiceImpl;
import myecomerce.userservice.domain.repository.UserRepository;
import myecomerce.userservice.infrastructure.mapper.IUserMapper;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public UserCommandService userCommandService(UserRepository userRepository, IUserMapper userMapper) {
        return new UserCommandServiceImpl(userRepository, userMapper);
    }

    @Bean
    public UserQueryService userQueryService(UserRepository userRepository, IUserMapper userMapper) {
        return new UserQueryServiceImpl(userRepository, userMapper);
    }

    @Bean
    public AuthService authService(UserRepository userRepository, PasswordHasher passwordHasher, TokenService tokenService) {
        return new AuthServiceImpl(userRepository, passwordHasher, tokenService);
    }
}