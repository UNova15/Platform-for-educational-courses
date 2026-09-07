package org.platform.platformforeducationalcourses.dto.auth;

import refactor.auth.application.ports.in.AuthResult;
import refactor.auth.domain.user.valueobject.UserRole;

public record AuthResponse(long id, String login, UserRole role, String accessToken) {
    public AuthResponse(AuthResult response) {
        this(response.id(), response.login(), response.role(), response.accessToken());
    }
}
