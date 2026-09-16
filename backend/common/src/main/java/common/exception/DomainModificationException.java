package common.exception;

import common.exception.codes.CommonExceptionCode;
import common.exception.codes.ExceptionCode;

import java.util.Map;

public class DomainModificationException extends BaseApplicationException {

    public DomainModificationException(ExceptionCode exceptionCode, String message, Map<String, Object> args) {
        super(exceptionCode, message, args);
    }

    public DomainModificationException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public DomainModificationException(String message) {
        super(CommonExceptionCode.DOMAIN_MODIFICATION_EXCEPTION, message);
    }
}
