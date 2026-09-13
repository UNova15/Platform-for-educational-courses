package refactor.infrastructure.accesstoken;

import refactor.auth.application.exceptions.InvalidTokenException;

public interface AccessTokenDecoder {
    TokenPayload parseAccessToken(String token) throws InvalidTokenException;
}
