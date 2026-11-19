package common.application.exception;

import common.domain.entity.AggregateType;
import common.domain.entity.Id;

public class ResourceAlreadyExist extends RuleConflictException {

    public ResourceAlreadyExist(AggregateType type, Id<?> aggregateId) {
        super("Resource: %s with id: %d already exists".formatted(type.value(), aggregateId.value()), type);
        withContext("aggregateId", aggregateId.value());
    }

    public ResourceAlreadyExist(AggregateType type) {
        super("Resource:%s already exists".formatted(type.value()), type);
    }
}
