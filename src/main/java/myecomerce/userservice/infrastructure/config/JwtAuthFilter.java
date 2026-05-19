package myecomerce.userservice.infrastructure.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myecomerce.userservice.application.authService.service.TokenService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
    private final TokenService tokenService;

    public JwtAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        
        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
                    if (tokenService.validate(token)) {
                        String userId = tokenService.extractUserId(token);

                        var auth = new UsernamePasswordAuthenticationToken(userId,null,null);

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
        }
        filterChain.doFilter(
                request,
                response
        );
    }
}
