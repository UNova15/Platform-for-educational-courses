package common.exception;

import common.exception.codes.CommonExceptionCode;
import common.exception.codes.ExceptionCode;

import java.util.Map;

public class DomainValidationException extends BaseApplicationException {
    public DomainValidationException(ExceptionCode exceptionCode, String message, Map<String, Object> args) {
        super(exceptionCode, message, args);
    }

    public DomainValidationException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public DomainValidationException(String message) {
        super(CommonExceptionCode.DOMAIN_VALIDATION_EXCEPTION, message);
    }
}
