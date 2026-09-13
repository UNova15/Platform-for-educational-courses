package refactor.common.exception;

import refactor.common.exception.codes.CommonExceptionCode;

public class DomainValidationException extends BaseApplicationException {
    public DomainValidationException(String message) {
        super(CommonExceptionCode.DOMAIN_VALIDATION_EXCEPTION, message);
    }
}
