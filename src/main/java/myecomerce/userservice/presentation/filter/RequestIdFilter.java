package myecomerce.userservice.presentation.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import myecomerce.userservice.presentation.apiResponse.RequestContext;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RequestIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get request ID from header
        String requestId = request.getHeader("X-Request-ID");
        // If request ID is not provided, generate a new one
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8);
        }
        // Set request ID to context
        RequestContext.setRequestId(requestId);
        // Add request ID to response header
        response.addHeader("X-Request-ID", requestId);
        // Do filter
        try {
            filterChain.doFilter(request, response);
        } finally {
            // Clear request ID from context
            RequestContext.clearRequestId();
        }
    }
}
