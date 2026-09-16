package kira.infrastructure.accesstoken;

import kira.infrastructure.api.TokenPayload;
import kira.infrastructure.exceptions.TokenDecodeException;

public interface AccessTokenDecoder {
    TokenPayload parseAccessToken(String token) throws TokenDecodeException;
}
