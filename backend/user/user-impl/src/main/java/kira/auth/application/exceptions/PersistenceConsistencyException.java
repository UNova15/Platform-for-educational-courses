package kira.auth.application.exceptions;

import common.domain.entity.AggregateType;
import common.domain.entity.Id;
import common.domain.exception.TypedBaseApplicationException;

public class PersistenceConsistencyException extends TypedBaseApplicationException {

    public PersistenceConsistencyException(String message, AggregateType type, Id<?> aggregateId) {
        super(message, type);
        withContext("aggregateId", aggregateId.value());
    }
}
