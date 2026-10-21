package kira.course.application.service.core.lesson;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import kira.course.application.port.in.lesson.status.PublishLessonUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseCheckStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleCheckStatusPort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.shared.CourseContentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LessonPublishService implements PublishLessonUseCase {
    private final LessonLoadPort loadPort;
    private final LessonSavePort savePort;

    private final ModuleCheckStatusPort moduleStatusPort;
    private final CourseAccessPort accessPort;
    private final CourseCheckStatusPort courseStatusPort;

    @Override
    public void publish(Id<User> requesterId, Id<Lesson> lessonId) {
        Lesson lesson = loadPort.loadLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.LESSON, lessonId));

        if (!accessPort.isLessonOwner(requesterId, lessonId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.LESSON, requesterId, lessonId);
        }

        CourseStatus courseStatus = courseStatusPort.findCourseStatusByLessonId(lessonId);
        if (courseStatus != CourseStatus.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish lesson in not published course", CourseAggregateType.LESSON, lessonId);
        }

        CourseContentStatus moduleStatus = moduleStatusPort.findStatusByLessonId(lessonId);
        if (moduleStatus != CourseContentStatus.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish lesson in not published module", CourseAggregateType.LESSON, lessonId);
        }

        lesson.publish();
        savePort.save(lesson);
    }
}
