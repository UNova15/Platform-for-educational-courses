package common.exception;

import common.exception.codes.CommonExceptionCode;

public class DomainValidationException extends BaseApplicationException {
    public DomainValidationException(String message) {
        super(CommonExceptionCode.DOMAIN_VALIDATION_EXCEPTION, message);
    }
}
