package refactor.course.application.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.exception.access.LessonAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.domain.LessonNotFoundException;
import refactor.course.application.port.in.lesson.create.LessonCreateCommand;
import refactor.course.application.port.in.lesson.create.LessonCreateResult;
import refactor.course.application.port.in.lesson.create.LessonCreateUseCase;
import refactor.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import refactor.course.application.port.in.lesson.update.LessonUpdateCommand;
import refactor.course.application.port.in.lesson.update.LessonUpdateUseCase;
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
    public LessonCreateResult createLesson(LessonCreateCommand createCommand, long teacherId, long moduleId) {
        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }

        var title = LessonTitle.of(createCommand.title());
        var content = Content.of(createCommand.type(), createCommand.content());

        Lesson lesson =
                Lesson.createNew(moduleId, title, content, createCommand.orderIndex(), createCommand.mandatory());

        Lesson savedLesson = savePort.save(lesson);
        return new LessonCreateResult(
                savedLesson.id(), moduleId, savedLesson.title().value());
    }

    @Override
    public void removeLesson(long teacherId, long lessonId) {
        if (!loadPort.isExist(lessonId)) {
            throw new LessonNotFoundException(lessonId);
        }

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new LessonAccessException(lessonId, teacherId);
        }

        removePort.removeById(lessonId);
    }

    @Override
    public void updateLesson(LessonUpdateCommand updateCommand, long lessonId, long teacherId) {
        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new LessonAccessException(lessonId, lessonId);
        }

        Lesson lesson = loadPort.loadLessonById(lessonId).orElseThrow(() -> new LessonNotFoundException(lessonId));

        var title = LessonTitle.of(updateCommand.title());
        var content = Content.of(updateCommand.type(), updateCommand.content());
        lesson.update(title, content, updateCommand.orderIndex(), updateCommand.mandatory());

        savePort.save(lesson);
    }
}
