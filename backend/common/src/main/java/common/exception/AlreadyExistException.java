package common.exception;

import refactor.common.domain.Id;
import refactor.common.exception.codes.CommonExceptionCode;
import refactor.common.exception.codes.ExceptionCode;

public abstract class AlreadyExistException extends BaseApplicationException {

    public AlreadyExistException(Id<?> resourceId) {
        super(
                CommonExceptionCode.ALREADY_EXIST_EXCEPTION,
                "Resource with ID: %s already exist".formatted(resourceId.value()));
        setProperty("resourceId", resourceId.value());
    }

    public AlreadyExistException(ExceptionCode code, String message) {
        super(code, message);
    }
}
