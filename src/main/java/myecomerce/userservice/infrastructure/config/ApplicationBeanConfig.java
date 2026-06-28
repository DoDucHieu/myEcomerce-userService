package myecomerce.userservice.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myecomerce.userservice.application.auditService.AuditService;
import myecomerce.userservice.application.auditService.AuditServiceImpl;
import myecomerce.userservice.application.authService.service.AuthService;
import myecomerce.userservice.application.authService.service.AuthServiceImpl;
import myecomerce.userservice.application.authService.service.OAuth2TokenService;
import myecomerce.userservice.application.authService.service.PasswordHasher;
import myecomerce.userservice.application.authService.service.TokenService;
import myecomerce.userservice.application.common.ICurrentUser;
import myecomerce.userservice.application.eventPublisherService.EventPublisherService;
import myecomerce.userservice.application.userService.service.UserCommandService;
import myecomerce.userservice.application.userService.service.UserCommandServiceImpl;
import myecomerce.userservice.application.userService.service.UserQueryService;
import myecomerce.userservice.application.userService.service.UserQueryServiceImpl;
import myecomerce.userservice.domain.repository.RefreshTokenRepository;
import myecomerce.userservice.domain.repository.RevokedTokenRepository;
import myecomerce.userservice.domain.repository.UserRepository;
import myecomerce.userservice.infrastructure.mapper.IUserMapper;
import myecomerce.userservice.infrastructure.rabbitmq.RabbitMqPublisher;
import myecomerce.userservice.infrastructure.common.CurrentUserImpl;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public UserCommandService userCommandService(UserRepository userRepository, IUserMapper userMapper) {
        return new UserCommandServiceImpl(userRepository, userMapper);
    }

    @Bean
    public UserQueryService userQueryService(UserRepository userRepository, IUserMapper userMapper, ICurrentUser currentUser) {
        return new UserQueryServiceImpl(userRepository, userMapper, currentUser);
    }

    @Bean
    public AuthService authService(
        UserRepository userRepository,
        RefreshTokenRepository refreshTokenRepository,
        RevokedTokenRepository revokedTokenRepository,
        PasswordHasher passwordHasher,
        TokenService tokenService,
        OAuth2TokenService oAuth2TokenService) {
        return new AuthServiceImpl(
            userRepository,
            refreshTokenRepository,
            revokedTokenRepository,
            passwordHasher,
            tokenService,
            oAuth2TokenService);
    }

    @Bean
    public ICurrentUser currentUser() {
        return new CurrentUserImpl();
    }

    @Bean
    public AuditService auditService(EventPublisherService eventPublisherService) {
        return new AuditServiceImpl(eventPublisherService);
    }

    @Bean
    public EventPublisherService eventPublisherService(RabbitTemplate rabbitTemplate) {
        return new RabbitMqPublisher (rabbitTemplate);
    }
}