package refactor.common.exception.access;

import lombok.Getter;

@Getter
public abstract class AccessException extends RuntimeException {
    private final long resourceId;
    private final long requesterId;

    public AccessException(long resourceId,long requesterId) {
        super("Resource with ID: %d requested from user with ID: %d not found".formatted(resourceId,resourceId));
        this.requesterId = requesterId;
        this.resourceId = resourceId;
    }
}
