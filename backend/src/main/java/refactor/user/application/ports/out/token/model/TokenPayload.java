package refactor.user.application.ports.out.token.model;

import refactor.user.domain.user.UserRole;

public record TokenPayload(long userId, String login, UserRole role) {}
