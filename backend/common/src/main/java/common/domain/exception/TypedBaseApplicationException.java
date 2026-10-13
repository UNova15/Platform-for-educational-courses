package common.domain.exception;

import common.domain.entity.AggregateType;
import lombok.Getter;

@Getter
public abstract class TypedBaseApplicationException extends BaseApplicationException {
    private final AggregateType type;

    public TypedBaseApplicationException(String message, AggregateType type) {
        super(message);
        this.type = type;
        withContext("aggregateType", type.value());
    }
}
