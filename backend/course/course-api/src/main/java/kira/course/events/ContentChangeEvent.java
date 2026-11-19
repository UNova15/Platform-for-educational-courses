package kira.course.events;

import common.domain.entity.DomainEvent;

public record ContentChangeEvent(
        ContentChangeType type, ChangeAction action, String name, long resourceId, boolean wasPublished)
        implements DomainEvent {

    public enum ChangeAction {
        UPDATE,
        REMOVE
    }

    public enum ContentChangeType {
        LESSON,
        TEST
    }
}
