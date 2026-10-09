package kira.course.application.port.out.persistance.module;

import common.domain.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.test.Test;

public interface ModuleStatusPort {
    boolean isPublishedByLessonId(Id<Lesson> lessonId);

    boolean isPublishedByTestId(Id<Test> testId);
}
