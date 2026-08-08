package refactor.auth.adapter.out.token;

import lombok.RequiredArgsConstructor;
import refactor.auth.application.ports.out.token.AccessTokenGeneratePort;
import refactor.auth.domain.user.UserRole;
import refactor.infrastructure.accesstoken.AccessTokenGenerator;

@RequiredArgsConstructor
class JwtTokenGenerator implements AccessTokenGeneratePort {
    private final AccessTokenGenerator generator;

    @Override
    public String generateAccessToken(long id, String login, UserRole role) {
        return generator.generateAccessToken(id, login, role.toString());
    }
}
