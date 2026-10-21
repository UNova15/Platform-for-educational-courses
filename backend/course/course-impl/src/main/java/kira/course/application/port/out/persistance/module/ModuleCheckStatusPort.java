package kira.course.application.port.out.persistance.module;

import common.domain.entity.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;

public interface ModuleStatusPort {
    CourseContentStatus findStatusByLessonId(Id<Lesson> lessonId);

    CourseContentStatus findStatusByTestId(Id<Test> testId);
}
