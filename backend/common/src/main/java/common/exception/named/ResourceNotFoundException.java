package common.exception.named;

import common.domain.Id;
import common.exception.core.BaseApplicationException;

public class ResourceNotFoundException extends BaseApplicationException {

    public ResourceNotFoundException(ResourceName name, Id<?> id) {
        super("Entity with name %s not found".formatted(name.value()));
        withContext("resourceName", name.value());
        withContext("id", id.value());

    }
}
