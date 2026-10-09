package kira.course.application.service.core.lesson;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonRemovePort;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonRemoveService implements LessonRemoveUseCase {
    private final CourseAccessPort accessPort;
    private final LessonLoadPort loadPort;
    private final LessonRemovePort removePort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public Result<Void, RemoveErrors> removeLesson(Id<User> teacherId, Id<Lesson> lessonId) {
        Optional<Lesson> loaded = loadPort.loadLessonById(lessonId);

        if (loaded.isEmpty()) {
            return Result.failure(RemoveErrors.LESSON_NOT_FOUND);
        }

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            return Result.failure(RemoveErrors.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByLessonId(lessonId)) {
            return Result.failure(RemoveErrors.COURSE_ARCHIVED);
        }

        var lesson = loaded.get();

        if (!lesson.isDraft()) {
            // TODO сделать каскадное удаление прогресса у студентнов через событие если урок опубликован
        }

        removePort.removeById(lessonId);
        return Result.success(null);
    }
}
