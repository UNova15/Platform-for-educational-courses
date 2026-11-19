package kira.progress.adapter.out.event;

import common.domain.entity.DomainEvent;
import kira.progress.application.port.out.events.EventPublicationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublicationAdapter implements EventPublicationPort {
    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }
}
