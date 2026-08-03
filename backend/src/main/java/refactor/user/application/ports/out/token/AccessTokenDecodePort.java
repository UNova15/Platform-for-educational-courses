package refactor.user.application.ports.out.token;

import refactor.common.exception.InvalidTokenException;
import refactor.user.application.ports.out.token.model.TokenPayload;

public interface AccessTokenDecodePort {
    TokenPayload parseToken(String token) throws InvalidTokenException;
}
