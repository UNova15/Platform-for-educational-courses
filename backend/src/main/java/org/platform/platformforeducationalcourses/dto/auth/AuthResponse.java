package org.platform.platformforeducationalcourses.dto.auth;

import org.platform.platformforeducationalcourses.domain.user.UserRole;

public record AuthResponse(long id, String login, UserRole role, String jwtToken) {
    public AuthResponse(TokenDto response) {
        this(response.id(), response.login(), response.role(), response.jwtToken());
    }
}
