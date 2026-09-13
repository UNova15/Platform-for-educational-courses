package refactor.common.exception;

import refactor.common.domain.Id;
import refactor.common.exception.codes.CommonExceptionCode;
import refactor.common.exception.codes.ExceptionCode;

public abstract class NotFoundException extends BaseApplicationException {

    public NotFoundException(String name, Id<?> resourceId) {
        super(
                CommonExceptionCode.MOT_FOUND_EXCEPTION,
                "Resource with name: %s ID: %s not found".formatted(name, resourceId.value()));
        setProperty("resourceId", resourceId.value());
    }

    public NotFoundException(ExceptionCode exceptionCode, String message, Id<?> resourceId) {
        super(exceptionCode, message);
        setProperty("resourceId", resourceId.value());
    }
}
