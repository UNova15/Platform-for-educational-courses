package common.exception.core;

import common.domain.Id;

public class DomainStateException extends BaseApplicationException {

    public DomainStateException(String message) {
        super(message);
    }

    public DomainStateException(String message, Id<?> id) {
        super(message);
        withContext("resourceId", id);
    }
}
