package refactor.course.application.service.factory;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.Lesson;
import refactor.course.application.port.in.course.command.create.CourseBulkLessonCommand;
import org.springframework.stereotype.Component;
import refactor.course.domain.lesson.LessonTitle;

@Component
@AllArgsConstructor
public class LessonFactory {
    public List<Lesson> fromLessonCreateCommand(List<CourseBulkLessonCommand> lessons, long moduleId) {
        return lessons.stream()
                .map(lesson -> fromLessonCreateCommand(lesson, moduleId))
                .toList();
    }

    public Lesson fromLessonCreateCommand(CourseBulkLessonCommand lesson, long moduleId) {
        return Lesson.createNew(
                moduleId,
                LessonTitle.of(lesson.title()),
                Content.of(lesson.type(), lesson.content()),
                lesson.orderIndex(),
                lesson.mandatory());
    }
}
