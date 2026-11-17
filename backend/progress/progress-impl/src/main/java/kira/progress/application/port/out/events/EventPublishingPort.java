package kira.progress.application.port.out.events;

import common.domain.entity.DomainEvent;

public interface EventPublishingPort {
    void publish(DomainEvent event);
}
