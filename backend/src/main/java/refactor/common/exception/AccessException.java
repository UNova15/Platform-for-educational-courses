package refactor.common.exception;

import refactor.common.domain.Id;
import refactor.common.exception.codes.CommonExceptionCode;
import refactor.common.exception.codes.ExceptionCode;

public abstract class AccessException extends BaseApplicationException {

    public AccessException(Id<?> resourceId, Id<?> requesterId) {
        super(
                CommonExceptionCode.ACCESS_EXCEPTION,
                "The user with ID: %s does not have access to the resource with ID: %s."
                        .formatted(requesterId.value(), resourceId.value()));
        setProperty("resourceId", resourceId.value());
        setProperty("requesterId", requesterId.value());
    }

    public AccessException(ExceptionCode exceptionCode, String message, Id<?> resourceId, Id<?> requesterId) {
        super(exceptionCode, message);
        setProperty("resourceId", resourceId.value());
        setProperty("requesterId", requesterId.value());
    }
}
