package kira.notification.application.port.out.render;

import kira.notification.domain.PublishType;
import kira.notification.domain.ResetType;

public interface MessageTemplatePort {
    Notification renderContentPublish(PublishType type, String name);

    Notification renderResetProgress(ResetType type, String name);

    Notification renderUserEnroll(String courseName, String userName);

    Notification renderUserCompletedTest(String studentName, String courseTitle, String testTile, long score);

    record Notification(String subject, String body) {}
}
