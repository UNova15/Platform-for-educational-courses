package refactor.auth.application.ports.out.token;

import refactor.auth.domain.user.UserRole;

public interface AccessTokenGeneratePort {
    String generateAccessToken(long id, String login, UserRole role);
}
