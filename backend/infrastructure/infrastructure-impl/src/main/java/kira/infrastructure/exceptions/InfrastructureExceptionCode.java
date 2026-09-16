package kira.infrastructure.exceptions;

import common.exception.codes.ExceptionCode;

public enum InfrastructureExceptionCode implements ExceptionCode {
    TOKEN_DECODE_EXCEPTION;

    @Override
    public String getExceptionCode() {
        return this.name();
    }
}
