package kira.infrastructure.accesstoken;

import kira.infrastructure.api.TokenPayload;

public interface AccessTokenDecoder {
    TokenPayload parseAccessToken(String token);
}
