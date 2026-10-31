package common.domain.exception;

import common.domain.entity.AggregateType;

public class DomainValidationException extends TypedBaseApplicationException {

    public DomainValidationException(String message, AggregateType type) {
        super(message, type);
    }
}
