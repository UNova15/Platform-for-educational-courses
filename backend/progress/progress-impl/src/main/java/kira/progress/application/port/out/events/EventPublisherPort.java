package kira.progress.application.port.out.events;

import common.domain.entity.DomainEvent;

public interface EventPublisherPort {
    void publish(DomainEvent event);
}
