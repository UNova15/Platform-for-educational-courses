package kira.infrastructure.exceptions;

import common.exception.BaseApplicationException;

public class TokenDecodeException extends BaseApplicationException {

    public TokenDecodeException(){
        super(InfrastructureExceptionCode.TOKEN_DECODE_EXCEPTION,"Error decoding token");
    }
}
