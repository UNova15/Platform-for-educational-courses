package common.exception.named;

import common.exception.core.RuleConflictException;

public class LengthViolationException extends RuleConflictException {

    public LengthViolationException(String message, ResourceName resource, int length, int max, int min) {
        super("Incorrect length resource: %s ".formatted(resource.value()));
        withContext("resourceName", resource.value());
        withContext("length", length);
        withContext("maxLength", max);
        withContext("minLength", min);
    }
}
