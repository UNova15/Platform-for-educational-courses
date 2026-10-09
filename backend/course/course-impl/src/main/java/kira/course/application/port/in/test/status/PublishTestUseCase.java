package kira.course.application.port.in.test.status;

import common.domain.Id;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;

public interface PublishTestUseCase {
    void publish(Id<User> requesterId, Id<Test> testId);
}
