package refactor.course.application.service.query;

import lombok.RequiredArgsConstructor;
import refactor.course.application.port.in.lesson.query.LessonQueryResult;
import org.springframework.stereotype.Service;
import refactor.course.application.port.in.lesson.query.LessonQueryUseCase;
import refactor.course.application.port.out.persistance.lesson.LessonQueryPort;

@Service
@RequiredArgsConstructor
public class LessonQueryService implements LessonQueryUseCase {
    private final LessonQueryPort queryPort;

    @Override
    public LessonQueryResult findLesson(long lessonId) {
        return queryPort.findLesson(lessonId);
    }
}
