package refactor.infrastructure.accesstoken;

import refactor.common.exception.auth.InvalidTokenException;

public interface AccessTokenDecoder {
    TokenPayload parseAccessToken(String token) throws InvalidTokenException;
}
