package refactor.common.exception;

import refactor.common.exception.codes.CommonExceptionCode;

public class DomainModificationException extends BaseApplicationException {

    public DomainModificationException(String message) {
        super(CommonExceptionCode.DOMAIN_MODIFICATION_EXCEPTION, message);
    }
}
