package myecomerce.userservice.infrastructure.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myecomerce.userservice.application.authService.exception.UnauthorizedException;
import myecomerce.userservice.application.authService.service.TokenService;
import myecomerce.userservice.domain.repository.RevokedTokenRepository;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
    private final HandlerExceptionResolver resolver;
    private final TokenService tokenService;
    private final RevokedTokenRepository revokedTokenRepository;

    public JwtAuthFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, TokenService tokenService, RevokedTokenRepository revokedTokenRepository) {
        this.resolver = resolver;
        this.tokenService = tokenService;
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try{

            String header = request.getHeader("Authorization");
            
            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                var tokenValid = tokenService.validate(token);

                var tokenRevoked = revokedTokenRepository.exists(token);

                if(!tokenValid || tokenRevoked){
                    throw new UnauthorizedException();
                }
                        
                String userId = tokenService.extractUserId(token);

                var auth = new UsernamePasswordAuthenticationToken(userId,null,null);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(
                    request,
                    response
            );
        }
        catch (Exception ex) {
            resolver.resolveException(
                request,
                response,
                null,   
                ex
            );
        }
    }
}
