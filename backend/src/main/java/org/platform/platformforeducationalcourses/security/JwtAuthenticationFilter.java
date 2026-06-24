package org.platform.platformforeducationalcourses.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.auth.ParsedToken;
import org.platform.platformforeducationalcourses.tokenutil.TokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println(">>> no Bearer, skipping");
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = header.substring(7);

        if (!tokenUtil.isValidToken(jwtToken)) {
            chain.doFilter(request, response);
            return;
        }

        ParsedToken token = tokenUtil.parseToken(jwtToken);

        UserDetails details = SecurityUser.fromJwt(token.userId(), token.login(), token.role());

        var authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
