package kira.course.application.service.core.lesson;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.lesson.status.PublishLessonUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.application.port.out.persistance.module.ModuleStatusPort;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonPublishService implements PublishLessonUseCase {
    private final LessonLoadPort loadPort;
    private final LessonSavePort savePort;

    private final ModuleStatusPort statusPort;
    private final CourseAccessPort accessPort;

    @Override
    public void publish(Id<User> requesterId, Id<Lesson> lessonId) {
        Lesson lesson = loadPort.loadLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.LESSON, lessonId));

        if (!statusPort.isPublishedByLessonId(lessonId)) {
            throw new RuleConflictException(
                    "Cannot publish lesson in not published module", CourseResourceName.LESSON, lessonId);
        }

        if (!accessPort.isLessonOwner(requesterId, lessonId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.LESSON, requesterId, lessonId);
        }

        if (!lesson.canPublish()) {
            throw new RuleConflictException(
                            "Cannot publish lesson in current status", CourseResourceName.LESSON, lessonId)
                    .withContext("status", lesson.status());
        }

        lesson.publish();
        savePort.save(lesson);
    }
}
