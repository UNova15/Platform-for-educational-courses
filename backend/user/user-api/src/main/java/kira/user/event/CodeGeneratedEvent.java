package kira.user.event;

import common.domain.entity.DomainEvent;

public record CodeGeneratedEvent(String email, String code) implements DomainEvent {}
