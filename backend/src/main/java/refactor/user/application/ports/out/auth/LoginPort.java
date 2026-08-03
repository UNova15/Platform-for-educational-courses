package refactor.user.application.ports.out.auth;

import refactor.user.domain.user.User;

public interface LoginPort {
    User login(String login, String password);
}
