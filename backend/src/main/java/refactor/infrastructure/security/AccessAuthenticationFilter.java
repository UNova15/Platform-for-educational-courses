package refactor.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import refactor.auth.application.exceptions.InvalidTokenException;
import refactor.infrastructure.accesstoken.AccessTokenDecoder;
import refactor.infrastructure.accesstoken.TokenPayload;

@Component
@RequiredArgsConstructor
class AccessAuthenticationFilter extends OncePerRequestFilter {
    private static final int ACCESS_TOKEN_START_INDEX = 7;

    private final AccessTokenDecoder accessTokenDecoder;

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
            TokenPayload payload = accessTokenDecoder.parseAccessToken(accessToken);

            var authorities = List.of(new SimpleGrantedAuthority("ROLE_".concat(payload.role())));

            var authentication =
                    new UsernamePasswordAuthenticationToken(payload, accessToken, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (InvalidTokenException exception) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
