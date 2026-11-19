package kira.user.event;

import common.domain.entity.DomainEvent;

public record RegistrationEvent(String email, String code) implements DomainEvent {}
