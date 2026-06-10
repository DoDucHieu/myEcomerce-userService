package myecomerce.userservice.infrastructure.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myecomerce.userservice.application.auditService.AuditEventCommand;
import myecomerce.userservice.application.auditService.AuditService;
import myecomerce.userservice.infrastructure.common.UserPrincipal;

import java.io.IOException;

@Component
public class AuditFilter extends OncePerRequestFilter{
    private final AuditService auditService;

    public AuditFilter(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
            long start = System.currentTimeMillis();
            try{
                filterChain.doFilter(request, response);
            }
            catch (Exception ex) {
                throw ex;
            }
            finally{
                long duration = System.currentTimeMillis() - start;
                String userId = "anonymous";
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getPrincipal() instanceof UserPrincipal principal) {
                    userId = principal.id().toString();
                }
                AuditEventCommand command = new AuditEventCommand(
                    userId,
                    "REQUEST",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    response.getStatus(),
                    duration
                );
                auditService.log(command);
            }
    }
}
