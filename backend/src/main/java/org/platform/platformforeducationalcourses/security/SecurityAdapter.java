package org.platform.platformforeducationalcourses.security;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.ports.security.SecurityPort;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.security.entity.SecurityUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityAdapter implements SecurityPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public User identify(String login, String password) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return User.restore(user.getId(), user.getPassword(), user.getUsername(), user.getRole());
    }
}
