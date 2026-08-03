package refactor.user.application.ports.in.command;

import refactor.user.domain.user.UserRole;

public record PairOfTokens(long id, String login, UserRole role, String accessToken, String refreshToken) {}
