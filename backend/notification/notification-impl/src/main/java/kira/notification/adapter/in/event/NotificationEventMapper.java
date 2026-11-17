package kira.notification.adapter.in.event;

import common.domain.entity.Id;
import java.util.List;
import kira.course.events.ContentPublishEvent;
import kira.notification.application.port.in.publish.ContentPublishNotification;
import kira.notification.application.port.in.resetprogress.ResetProgressNotification;
import kira.notification.domain.PublishType;
import kira.notification.domain.ResetType;
import kira.notification.domain.marker.User;
import kira.progress.event.ResetProgressEvent;

public class NotificationEventMapper {

    public static ContentPublishNotification mapToContentPublishNotification(ContentPublishEvent event) {
        PublishType action =
                switch (event.type()) {
                    case LESSON -> PublishType.LESSON;
                    case TEST -> PublishType.TEST;
                    case MODULE -> PublishType.MODULE;
                };

        return new ContentPublishNotification(action, event.name(), Id.of(event.courseId()), Id.of(event.resourceId()));
    }

    public static ResetProgressNotification mapToResetProgressNotification(ResetProgressEvent event) {
        ResetType type =
                switch (event.resource()) {
                    case TEST -> ResetType.TEST;
                    case LESSON -> ResetType.LESSON;
                };

        List<Id<User>> userIds = event.studentsId().stream().map(Id::<User>of).toList();
        return new ResetProgressNotification(type, event.name(), userIds);
    }
}
