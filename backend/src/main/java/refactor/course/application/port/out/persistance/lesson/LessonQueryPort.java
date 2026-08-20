package refactor.course.application.port.out.persistance.lesson;

import refactor.course.application.port.in.lesson.query.LessonQueryResult;

public interface LessonQueryPort {
    LessonQueryResult findLesson(long lessonId);
}
