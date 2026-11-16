package kira.progress.event;

import common.domain.entity.DomainEvent;
import java.util.List;

public record ResetProgressEvent(ResourceType resource, ResetReason reason, String name, List<Long> studentsId)
        implements DomainEvent {

    public enum ResetReason {
        REMOVE,
        UPDATE
    }

    public enum ResourceType {
        LESSON,
        TEST
    }
}
