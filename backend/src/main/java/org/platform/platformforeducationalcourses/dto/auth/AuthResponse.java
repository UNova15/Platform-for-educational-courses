package org.platform.platformforeducationalcourses.dto.auth;

import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.domain.user.UserRole;

public record AuthResponse(long id, String login, UserRole role, String accessToken) {
    public AuthResponse(PairOfTokens response) {
        this(response.id(), response.login(), response.role(), response.accessToken());
    }
}
