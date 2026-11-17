package kira.progress.application.port.out.events;

import common.domain.entity.DomainEvent;

public interface EventPublicationPort {
    void publish(DomainEvent event);
}
