package kira.course.application.port.in.lesson.status;

import common.domain.entity.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;

public interface PublishLessonUseCase {
    void publish(Id<User> requesterId, Id<Lesson> lessonId);
}
