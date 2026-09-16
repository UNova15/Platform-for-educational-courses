package common.exception;

import common.domain.Id;
import common.exception.codes.ExceptionCode;

public class ResourceAccessException extends BaseApplicationException {
    private static final String TEMPLATE = "Error access user with ID: %s to resource '%s' with ID: %s ";

    public <T> ResourceAccessException(
            ExceptionCode exceptionCode, Class<T> resourceClass, Id<?> requesterId, Id<T> resourceId) {

        super(
                exceptionCode,
                TEMPLATE.formatted(requesterId.value(), resourceClass.getSimpleName(), resourceId.value()));
        setProperty("resourceName", resourceClass.getSimpleName());
        setProperty("resourceId", resourceId.value());
        setProperty("requesterId", requesterId.value());
    }
}
