package refactor.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import refactor.common.exception.InvalidTokenException;
import refactor.user.adapter.out.security.model.SecurityUser;
import refactor.user.application.ports.out.token.AccessTokenDecodePort;
import refactor.user.application.ports.out.token.model.TokenPayload;

@Component
@RequiredArgsConstructor
public class AccessAuthenticationFilter extends OncePerRequestFilter {
    private static final int ACCESS_TOKEN_START_INDEX = 7;

    private final AccessTokenDecodePort accessTokenDecodePort;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = header.substring(ACCESS_TOKEN_START_INDEX);

        if (accessToken.isBlank()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            TokenPayload payload = accessTokenDecodePort.parseToken(accessToken);

            UserDetails principal = SecurityUser.fromAccessToken(payload.userId(), payload.login(), payload.role());

            var authentication =
                    new UsernamePasswordAuthenticationToken(principal, accessToken, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (InvalidTokenException exception) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
