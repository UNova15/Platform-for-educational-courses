package refactor.auth.application.ports.out.token;

import refactor.auth.domain.token.valueobject.AccessToken;
import refactor.auth.domain.user.User;
import refactor.auth.domain.user.valueobject.Login;
import refactor.auth.domain.user.valueobject.UserRole;
import refactor.common.domain.Id;

public interface AccessTokenGeneratePort {
    AccessToken generateAccessToken(Id<User> id, Login login, UserRole role);
}
