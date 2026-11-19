package kira.user.application.ports.out.token;

import common.domain.entity.Id;
import kira.user.domain.token.valueobject.AccessToken;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Login;

public interface AccessTokenGeneratePort {
    AccessToken generateAccessToken(Id<User> id, Login login);
}
