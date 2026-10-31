package kira.course.application.port.out.persistance.module;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;

public interface ModuleCheckStatusPort {
    CourseContentStatus findStatusByLessonId(Id<Lesson> lessonId);

    CourseContentStatus findStatusByTestId(Id<Test> testId);

    Optional<CourseContentStatus> findStatusById(Id<CourseModule> moduleId);
}
