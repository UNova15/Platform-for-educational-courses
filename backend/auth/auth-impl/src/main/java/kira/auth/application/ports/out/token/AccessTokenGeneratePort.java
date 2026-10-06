package kira.auth.application.ports.out.token;

import common.domain.Id;
import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;

public interface AccessTokenGeneratePort {
    AccessToken generateAccessToken(Id<User> id, Login login);
}
