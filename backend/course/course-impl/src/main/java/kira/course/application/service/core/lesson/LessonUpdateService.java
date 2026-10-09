package kira.course.application.service.core.lesson;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.lesson.update.LessonUpdateCommand;
import kira.course.application.port.in.lesson.update.LessonUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonUpdateService implements LessonUpdateUseCase {
    private final LessonLoadPort loadPort;
    private final LessonSavePort savePort;

    private final CourseAccessPort accessPort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public Result<Void, LessonUpdateUseCase.UpdateError> updateLesson(
            LessonUpdateCommand updateCommand, Id<Lesson> lessonId, Id<User> teacherId) {
        Optional<Lesson> loaded = loadPort.loadLessonById(lessonId);

        if (loaded.isEmpty()) {
            return Result.failure(LessonUpdateUseCase.UpdateError.LESSON_NOT_FOUND);
        }

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            return Result.failure(LessonUpdateUseCase.UpdateError.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByLessonId(lessonId)) {
            return Result.failure(LessonUpdateUseCase.UpdateError.COURSE_ARCHIVED);
        }

        var lesson = loaded.get();

        var title = Title.of(updateCommand.title());
        var content = LessonContent.of(updateCommand.type(), updateCommand.content());
        lesson.update(title, content, updateCommand.orderIndex(), updateCommand.mandatory());

        if (!lesson.isDraft()) {
            // TODO сделать каскадное удаление прогресса у студентнов через событие если урок опубликован
        }
        savePort.save(lesson);

        return Result.success(null);
    }
}
