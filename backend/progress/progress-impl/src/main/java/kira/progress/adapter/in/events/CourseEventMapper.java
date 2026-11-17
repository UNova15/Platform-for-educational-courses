package kira.progress.adapter.in.events;

import common.domain.entity.Id;
import kira.course.events.ContentChangeEvent;
import kira.progress.application.port.in.resetprogress.ResetRecord;
import kira.progress.event.ResetProgressEvent;

class CourseEventMapper {

    public static ResetRecord mapToResetRecord(ContentChangeEvent event) {
        ResetProgressEvent.ResetReason action =
                switch (event.action()) {
                    case REMOVE -> ResetProgressEvent.ResetReason.REMOVE;
                    case UPDATE -> ResetProgressEvent.ResetReason.UPDATE;
                };

        ResetProgressEvent.ResourceType resource =
                switch (event.type()) {
                    case TEST -> ResetProgressEvent.ResourceType.TEST;
                    case LESSON -> ResetProgressEvent.ResourceType.LESSON;
                };

        return new ResetRecord(resource, action, event.name(), Id.of(event.resourceId()));
    }
}
