package kira.course.application.port.in.module.status;

import common.domain.entity.Id;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;

public interface PublishModuleUseCase {
    void publish(Id<User> requesterId, Id<CourseModule> moduleId);
}
