package org.platform.platformforeducationalcourses.dto.auth;

import org.platform.platformforeducationalcourses.domain.user.Role;

public record AuthResponse(long id, String login, Role role, String jwtToken) {
    public AuthResponse(TokenDto response) {
        this(response.id(), response.login(), response.role(), response.jwtToken());
    }
}
