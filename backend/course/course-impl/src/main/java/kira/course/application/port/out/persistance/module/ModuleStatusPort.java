package kira.course.application.port.out.persistance.module;

import common.domain.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.shared.Status;
import kira.course.domain.test.Test;

public interface ModuleStatusPort {
    Status findStatusByLessonId(Id<Lesson> lessonId);

    Status findStatusByTestId(Id<Test> testId);
}
