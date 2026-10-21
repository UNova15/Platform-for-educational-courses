package common.domain.exception;

import common.domain.entity.Id;
import common.domain.entity.AggregateType;

public class BusinessRuleConflictException extends TypedBaseApplicationException {

    public BusinessRuleConflictException(String message, AggregateType type) {
        super(message, type);
    }

    public BusinessRuleConflictException(String message, AggregateType type, Id<?> aggregateId) {
        super(message, type);
        withContext("aggregateId", aggregateId.value());
    }
}
