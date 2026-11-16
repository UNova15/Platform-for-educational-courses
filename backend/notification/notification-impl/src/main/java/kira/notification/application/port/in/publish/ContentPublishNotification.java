package kira.notification.application.port.in.publish;

import common.domain.entity.Id;
import kira.notification.domain.Course;
import kira.notification.domain.PublishType;

public record ContentPublishNotification(
        PublishType type, String name, Id<Course> courseId, Id<PublishType> resourceTypeId) {}
