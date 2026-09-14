package auth.application.ports.out.token;

import refactor.auth.implemetnation.domain.token.valueobject.AccessToken;
import refactor.auth.implemetnation.domain.user.User;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.auth.implemetnation.domain.user.valueobject.UserRole;
import refactor.common.domain.Id;

public interface AccessTokenGeneratePort {
    AccessToken generateAccessToken(Id<User> id, Login login, UserRole role);
}
