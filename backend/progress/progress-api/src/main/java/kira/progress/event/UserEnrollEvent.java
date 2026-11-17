package kira.progress.event;

import common.domain.entity.DomainEvent;

public record UserEnrollEvent(long userId, long courseId) implements DomainEvent {}
