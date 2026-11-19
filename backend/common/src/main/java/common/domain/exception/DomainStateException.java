package common.domain.exception;

import common.domain.entity.AggregateType;
import common.domain.entity.Id;

public class DomainStateException extends TypedBaseApplicationException {

    public DomainStateException(String message, AggregateType type) {
        super(message, type);
    }

    public DomainStateException(String message, AggregateType type, Id<?> id) {
        super(message, type);
        withContext("aggregateId", id);
    }
}
