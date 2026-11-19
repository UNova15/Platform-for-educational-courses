package kira.user.application.ports.out.event;

import common.domain.entity.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
