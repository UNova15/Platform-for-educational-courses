package common.exception.core;

import common.domain.Id;
import common.exception.named.ResourceName;

public class RuleConflictException extends BaseApplicationException {

    public RuleConflictException(String message) {
        super(message);
    }

    public RuleConflictException(String message, ResourceName name, Id<?> resourceId) {
        super(message);
        withContext("resourceName", name.value());
        withContext("resourceId", resourceId.value());
    }
}
