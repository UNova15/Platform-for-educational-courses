package common.application.exception;

import common.domain.entity.Id;
import common.domain.exception.TypedBaseApplicationException;
import common.domain.entity.AggregateType;

public class ResourceAccessDeniedException extends TypedBaseApplicationException {

    public ResourceAccessDeniedException(AggregateType type, Id<?> requesterId, Id<?> aggregateId) {
        super(
                "Access to the resource with the name: %s and id: %d is denied to the user with id: %d"
                        .formatted(type.value(), aggregateId.value(), requesterId.value()),
                type);
        withContext("aggregateId", aggregateId.value());
        withContext("requesterId", requesterId.value());
    }
}
