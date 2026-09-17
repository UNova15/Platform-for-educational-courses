package common.exception;

import common.domain.Id;
import common.exception.codes.ExceptionCode;

import java.util.Map;

public class ResourceAlreadyExistException extends BaseApplicationException {
    private static final String TEMPLATE = "Resource '%s' with ID: %s already exist";

    public <T> ResourceAlreadyExistException(ExceptionCode exceptionCode, Class<T> resourceClass, Id<T> resourceId) {
        super(exceptionCode, TEMPLATE.formatted(resourceClass.getSimpleName(), resourceId.value()));
        setProperty("resourceName", resourceClass.getSimpleName());
        setProperty("resourceId", resourceId.value());
    }

    public ResourceAlreadyExistException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
