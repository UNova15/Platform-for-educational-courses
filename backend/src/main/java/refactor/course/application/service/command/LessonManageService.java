package refactor.course.application.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.exception.access.LessonAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.domain.LessonNotFoundException;
import refactor.course.application.port.in.lesson.command.create.LessonCreateCommand;
import refactor.course.application.port.in.lesson.command.create.LessonCreateResult;
import refactor.course.application.port.in.lesson.command.create.LessonCreateUseCase;
import refactor.course.application.port.in.lesson.command.remove.LessonRemoveCommand;
import refactor.course.application.port.in.lesson.command.remove.LessonRemoveUseCase;
import refactor.course.application.port.in.lesson.command.update.LessonUpdateCommand;
import refactor.course.application.port.in.lesson.command.update.LessonUpdateUseCase;
import refactor.course.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.application.port.out.persistance.lesson.LessonLoadPort;
import refactor.course.application.port.out.persistance.lesson.LessonRemovePort;
import refactor.course.application.port.out.persistance.lesson.LessonSavePort;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.lesson.LessonTitle;

@Service
@RequiredArgsConstructor
public class LessonManageService implements LessonCreateUseCase, LessonRemoveUseCase, LessonUpdateUseCase {
    private final LessonSavePort savePort;
    private final CourseAccessPort accessPort;
    private final LessonLoadPort loadPort;
    private final LessonRemovePort removePort;

    @Override
    public LessonCreateResult createLesson(LessonCreateCommand createCommand) {
        if (!accessPort.canManageModule(createCommand.teacherId(), createCommand.moduleId())) {
            throw new ModuleAccessException(createCommand.moduleId(), createCommand.teacherId());
        }

        var title = LessonTitle.of(createCommand.title());
        var content = Content.of(createCommand.type(), createCommand.content());

        Lesson lesson = Lesson.createNew(
                createCommand.moduleId(), title, content, createCommand.orderIndex(), createCommand.mandatory());

        Lesson savedLesson = savePort.save(lesson);
        return new LessonCreateResult(
                savedLesson.id(), createCommand.moduleId(), savedLesson.title().value());
    }

    @Override
    public void removeLesson(LessonRemoveCommand command) {
        if (!loadPort.isExist(command.lessonId())) {
            throw new LessonNotFoundException(command.lessonId());
        }

        if (!accessPort.canManageLesson(command.teacherId(), command.lessonId())) {
            throw new LessonAccessException(command.lessonId(), command.teacherId());
        }

        removePort.removeById(command.lessonId());
    }

    @Override
    public void updateLesson(LessonUpdateCommand updateCommand) {
        if (!accessPort.canManageLesson(updateCommand.teacherId(), updateCommand.lessonId())) {
            throw new LessonAccessException(updateCommand.lessonId(), updateCommand.lessonId());
        }

        Lesson lesson = loadPort.loadLessonById(updateCommand.lessonId())
                .orElseThrow(() -> new LessonNotFoundException(updateCommand.lessonId()));

        var title = LessonTitle.of(updateCommand.title());
        var content = Content.of(updateCommand.type(), updateCommand.content());
        lesson.update(title, content, updateCommand.orderIndex(), updateCommand.mandatory());

        savePort.save(lesson);
    }
}
