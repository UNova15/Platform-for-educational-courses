package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;

import java.util.Optional;

public interface LessonProviderPort {
    Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId);
}
