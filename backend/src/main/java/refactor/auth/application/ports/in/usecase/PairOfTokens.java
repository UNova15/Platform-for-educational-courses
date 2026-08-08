package refactor.auth.application.ports.in.usecase;

import refactor.auth.domain.user.UserRole;

public record PairOfTokens(long id, String login, UserRole role, String accessToken, String refreshToken) {}
