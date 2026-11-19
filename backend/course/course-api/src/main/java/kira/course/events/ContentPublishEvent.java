package kira.course.events;

import common.domain.entity.DomainEvent;

public record ContentPublishEvent(ContentPublishType type, String name, long courseId, long resourceId)
        implements DomainEvent {

    public enum ContentPublishType {
        LESSON,
        TEST,
        MODULE
    }
}
