package org.platform.platformforeducationalcourses.dto.auth;

import refactor.auth.application.ports.in.PairOfTokens;
import refactor.auth.domain.user.UserRole;

public record AuthResponse(long id, String login, UserRole role, String accessToken) {
    public AuthResponse(PairOfTokens response) {
        this(response.id(), response.login(), response.role(), response.accessToken());
    }
}
