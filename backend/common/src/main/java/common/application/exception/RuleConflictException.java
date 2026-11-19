package common.application.exception;

import common.domain.entity.AggregateType;
import common.domain.entity.Id;
import common.domain.exception.TypedBaseApplicationException;

public class RuleConflictException extends TypedBaseApplicationException {

    public RuleConflictException(String message, AggregateType type) {
        super(message, type);
    }

    public RuleConflictException(String message, AggregateType type, Id<?> aggregateId) {
        super(message, type);
        withContext("aggregateId", aggregateId.value());
    }
}
