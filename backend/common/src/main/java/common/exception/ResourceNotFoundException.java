package common.exception;

import common.domain.Id;
import common.exception.codes.ExceptionCode;

public class ResourceNotFoundException extends BaseApplicationException {
    private static final String TEMPLATE = "Resource '%s' with ID: %s not found";

    public <T> ResourceNotFoundException(ExceptionCode exceptionCode, Class<T> resourceClass, Id<T> resourceId) {
        super(exceptionCode, TEMPLATE.formatted(resourceClass.getSimpleName(), resourceId.value()));
        setProperty("resourceName", resourceClass.getSimpleName());
        setProperty("resourceId", resourceId.value());
    }

    public <T> ResourceNotFoundException(ExceptionCode exceptionCode, Class<T> resourceClass, Object resourceId) {
        super(exceptionCode, TEMPLATE.formatted(resourceClass.getSimpleName(), resourceId));
        setProperty("resourceName", resourceClass.getSimpleName());
        setProperty("resourceId", resourceId);
    }
}
