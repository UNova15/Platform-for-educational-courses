package kira.notification.application.port.in.publish;

import common.domain.entity.Id;
import kira.notification.domain.PublishType;
import kira.notification.domain.marker.Course;

public record ContentPublishNotification(
        PublishType type, String name, Id<Course> courseId, Id<PublishType> resourceTypeId) {}
