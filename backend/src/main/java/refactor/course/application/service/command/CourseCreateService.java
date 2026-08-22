package refactor.course.application.service.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import refactor.course.application.port.in.course.command.create.CourseCreateCommand;
import refactor.course.application.port.in.course.command.create.CourseCreateResult;
import refactor.course.application.service.factory.LessonFactory;
import refactor.course.application.service.factory.TestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refactor.course.application.port.in.course.command.create.CourseCreateUseCase;
import refactor.course.application.port.out.persistance.course.CourseSavePort;
import refactor.course.application.port.out.persistance.lesson.LessonSavePort;
import refactor.course.application.port.out.persistance.module.ModuleSavePort;
import refactor.course.application.port.out.persistance.test.TestSavePort;
import refactor.course.application.service.factory.ModuleFactory;
import refactor.course.domain.course.Course;
import refactor.course.domain.course.CourseDescription;
import refactor.course.domain.course.CourseTitle;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.test.Test;

// В текущем варианте валидный курс обязательно содержит 1 модуль и 1 урок. В будущем введется понятие статуса
// курс(DRAFT,PUBLISHED, ARCHIVED). Тогда доменный класс Course будет иметь метод publish и принимать список курсов и
// список уроков для контроля своего инварианта

@Service
@AllArgsConstructor
public class CourseCreateService implements CourseCreateUseCase {
    private final CourseSavePort courseSavePort;

    private final ModuleSavePort moduleSavePort;
    private final LessonSavePort lessonSavePort;
    private final TestSavePort testSavePort;

    private final ModuleFactory moduleFactory;
    private final LessonFactory lessonFactory;
    private final TestFactory testFactory;

    @Transactional
    public CourseCreateResult createCourseWithContent(CourseCreateCommand command, long userId) {
        Course course = Course.createNew(
                userId, CourseTitle.of(command.title()), CourseDescription.of(command.description()), command.tag());
        Course savedCourse = courseSavePort.save(course);

        List<CourseModule> modules = moduleFactory.fromCommand(command.modules(), savedCourse.id());
        List<CourseModule> savedModules = moduleSavePort.saveAll(modules);

        Map<Integer, Long> orderedModules =
                savedModules.stream().collect(Collectors.toMap(CourseModule::orderIndex, CourseModule::id));

        List<Lesson> lessonsToSave = command.modules().stream()
                .filter(module -> module.lessons() != null && !module.lessons().isEmpty())
                .map(module -> lessonFactory.fromLessonCreateCommand(
                        module.lessons(), orderedModules.get(module.orderIndex())))
                .flatMap(Collection::stream)
                .toList();

        List<Test> testToSave = command.modules().stream()
                .filter(module -> module.tests() != null && !module.tests().isEmpty())
                .map(module ->
                        testFactory.fromBulkTestCreateCommand(module.tests(), orderedModules.get(module.orderIndex())))
                .flatMap(Collection::stream)
                .toList();

        lessonSavePort.saveAll(lessonsToSave);
        testSavePort.saveAll(testToSave);

        return new CourseCreateResult(savedCourse.id(), command.title(), savedCourse.createdAt());
    }
}
