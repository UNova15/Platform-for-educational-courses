package kira.notification.application.port.in;

import common.domain.entity.Id;
import kira.notification.domain.marker.Test;
import kira.notification.domain.marker.User;

public interface CompletedTestNotifyUseCase {
    void sendCompletedTestNotification(Id<User> studentId, Id<Test> testId, long score);
}
