package kira.progress.event;

import common.domain.entity.DomainEvent;

public record CompletedTestEvent(long studentId, long testId, long score) implements DomainEvent {}
