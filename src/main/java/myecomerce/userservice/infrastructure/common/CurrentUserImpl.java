package myecomerce.userservice.infrastructure.common;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import myecomerce.userservice.application.common.ICurrentUser;
import myecomerce.userservice.domain.model.UserRole;

public class CurrentUserImpl implements ICurrentUser{

    @Override
    public UUID getId() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UserPrincipal principal =
                (UserPrincipal)
                        authentication.getPrincipal();

        return principal.id();
    }

    @Override
    public String getEmail() {
                Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UserPrincipal principal =
                (UserPrincipal)
                        authentication.getPrincipal();

        return principal.email();
    }

    @Override
    public UserRole getRole() {
                Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UserPrincipal principal =
                (UserPrincipal)
                        authentication.getPrincipal();

        return principal.role();
    }
    
}
