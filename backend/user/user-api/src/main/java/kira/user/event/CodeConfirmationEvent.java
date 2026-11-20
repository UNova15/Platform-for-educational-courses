package kira.user.event;

import common.domain.entity.DomainEvent;

public record CodeConfirmationEvent(String email, String code) implements DomainEvent {}
