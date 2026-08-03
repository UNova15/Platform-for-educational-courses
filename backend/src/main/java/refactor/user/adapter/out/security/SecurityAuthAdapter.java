package refactor.user.adapter.out.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import refactor.common.exception.AuthenticationException;
import refactor.user.adapter.out.security.model.SecurityUser;
import refactor.user.application.ports.out.auth.LoginPort;
import refactor.user.domain.user.User;

@Component
@AllArgsConstructor
public class SecurityAuthAdapter implements LoginPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public User login(String login, String password) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

            SecurityUser user = (SecurityUser) authentication.getPrincipal();

            return User.restore(user.getId(), user.getPassword(), user.getUsername(), user.getRole());
        } catch (BadCredentialsException exception) {
            throw new AuthenticationException("Invalid login or password for user with login: %s".formatted(login));
        }
    }
}
