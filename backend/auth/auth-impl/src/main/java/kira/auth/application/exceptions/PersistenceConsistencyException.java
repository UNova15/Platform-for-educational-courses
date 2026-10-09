package kira.auth.application.exceptions;

import common.domain.Id;
import common.exception.core.BaseApplicationException;

public class PersistenceConsistencyException extends BaseApplicationException {

    public PersistenceConsistencyException(String message, Id<?> resourceId) {
        super(message);
        withContext("resourceId", resourceId.value());
    }
}
