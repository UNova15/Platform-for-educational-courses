package kira.auth.application.ports.out.token;


import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.UserRole;
import common.domain.Id;

public interface AccessTokenGeneratePort {
    AccessToken generateAccessToken(Id<User> id, Login login, UserRole role);
}
