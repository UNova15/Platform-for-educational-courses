package common.exception.named;

import common.domain.Id;
import common.exception.core.BaseApplicationException;

public class ResourceAccessDeniedException extends BaseApplicationException {

    public ResourceAccessDeniedException(ResourceName resourceName, Id<?> requesterId, Id<?> resourceId) {
        super("Access to the resource with the name: %s and id: %d is denied to the user with id: %d"
                .formatted(resourceName.value(), resourceId.value(), requesterId.value()));
        withContext("resourceName", resourceName.value());
        withContext("resourceId", resourceId.value());
        withContext("requesterId", requesterId.value());
    }
}
