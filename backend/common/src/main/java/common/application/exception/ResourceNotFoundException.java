package common.application.exception;

import common.domain.entity.Id;
import common.domain.exception.TypedBaseApplicationException;
import common.domain.entity.AggregateType;

public class ResourceNotFoundException extends TypedBaseApplicationException {

    public ResourceNotFoundException(AggregateType type, Id<?> aggregateId) {
        super("Entity with name %s not found".formatted(type.value()), type);
        withContext("aggregateId", aggregateId.value());
    }

    public ResourceNotFoundException(AggregateType type) {
        super("Entity with name %s not found".formatted(type.value()), type);
    }
}
