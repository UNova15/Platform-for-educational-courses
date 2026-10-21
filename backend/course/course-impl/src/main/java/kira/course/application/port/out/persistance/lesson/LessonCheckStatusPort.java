package kira.course.application.port.out.persistance.lesson;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.shared.CourseContentStatus;

public interface LessonCheckStatusPort {
    Optional<CourseContentStatus> findLessonStatusById(Id<Lesson> lessonId);
}
