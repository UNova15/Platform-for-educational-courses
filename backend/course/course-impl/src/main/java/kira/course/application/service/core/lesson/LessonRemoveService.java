package kira.course.application.service.core.lesson;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonRemovePort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LessonRemoveService implements LessonRemoveUseCase {
    private final CourseAccessPort accessPort;
    private final LessonLoadPort loadPort;
    private final LessonRemovePort removePort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public void removeLesson(Id<User> teacherId, Id<Lesson> lessonId) {
        Lesson lesson = loadPort.loadLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.LESSON, lessonId));

        if (!accessPort.isLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.LESSON, teacherId, lessonId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByLessonId(lessonId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException(
                    "Cannot remove lesson in archived course", CourseResourceName.LESSON, lessonId);
        }

        if (!lesson.isDraft()) {
            // TODO сделать каскадное удаление прогресса у студентнов через событие если урок опубликован
        }

        removePort.removeById(lessonId);
    }
}
