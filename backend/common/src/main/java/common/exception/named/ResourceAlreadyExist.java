package common.exception.named;

import common.domain.Id;
import common.exception.core.BaseApplicationException;

public class ResourceAlreadyExist extends BaseApplicationException {

    public ResourceAlreadyExist(ResourceName name, Id<?> resourceId) {
        super("Resource: %s with id: %d already exists".formatted(name.value(), resourceId.value()));
        withContext("resourceName", name.value());
        withContext("resourceId", resourceId.value());
    }

    public ResourceAlreadyExist(ResourceName name) {
        super("Resource: %s already exists".formatted(name.value()));
        withContext("resourceName", name.value());
    }
}
