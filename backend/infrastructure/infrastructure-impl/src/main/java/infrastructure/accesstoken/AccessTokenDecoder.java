package infrastructure.accesstoken;

import refactor.auth.implemetnation.application.exceptions.InvalidTokenException;

public interface AccessTokenDecoder {
    TokenPayload parseAccessToken(String token) throws InvalidTokenException;
}
