package kira.course.events;

import common.domain.entity.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class LessonUpdatedEvent extends DomainEvent {
    private final long lessonId;
    private final boolean wasPublished;
}
