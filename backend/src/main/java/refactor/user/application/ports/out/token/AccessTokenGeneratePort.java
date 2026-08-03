package refactor.user.application.ports.out.token;

import refactor.user.domain.user.UserRole;

public interface AccessTokenGeneratePort {
    String generateAccessToken(long id, String login, UserRole role);
}
