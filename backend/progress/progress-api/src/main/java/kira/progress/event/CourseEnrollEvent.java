package kira.progress.event;

import common.domain.entity.DomainEvent;

public record CourseEnrollEvent(long userId, long courseId) implements DomainEvent {}
