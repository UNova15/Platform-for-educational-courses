package refactor.course.application.service.factory;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.common.domain.Id;
import refactor.course.application.port.in.course.create.CourseCreateCommand;
import refactor.course.domain.common.Title;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.Lesson;
import org.springframework.stereotype.Component;
import refactor.course.domain.module.CourseModule;

@Component
@AllArgsConstructor
public class LessonFactory {
    public List<Lesson> fromLessonCreateCommand(List<CourseCreateCommand.LessonCommand> lessons, Id<CourseModule> moduleId) {
        return lessons.stream()
                .map(lesson -> fromLessonCreateCommand(lesson, moduleId))
                .toList();
    }

    public Lesson fromLessonCreateCommand(CourseCreateCommand.LessonCommand lesson, Id<CourseModule> moduleId) {
        return Lesson.createNew(
                moduleId,
                Title.of(lesson.title()),
                Content.of(lesson.type(), lesson.content()),
                lesson.orderIndex(),
                lesson.mandatory());
    }
}
