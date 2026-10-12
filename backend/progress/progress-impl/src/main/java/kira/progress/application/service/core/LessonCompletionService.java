package kira.progress.application.service.core;

import common.domain.Id;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.progress.application.port.in.lessonprogress.LessonCompletionUseCase;
import kira.progress.application.port.out.external.CourseStructurePort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressLoadPort;
import kira.progress.application.port.out.persistance.lessonprogress.LessonProgressSavePort;
import kira.progress.application.exception.ProgressResourceName;
import kira.progress.domain.lessonprogress.LessonProgress;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LessonCompletionService implements LessonCompletionUseCase {
    private final CourseStructurePort courseStructurePort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final LessonProgressLoadPort lessonProgressLoadPort;
    private final LessonProgressSavePort lessonProgressSavePort;

    //TODO првоерить что урок опубликован
    @Override
    public void completeLesson(Id<User> userId, Id<Lesson> lessonId) {
        Id<Course> courseId = courseStructurePort
                .findCourseIdByLessonId(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(ProgressResourceName.LESSON, lessonId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new ResourceAccessDeniedException(ProgressResourceName.COURSE, userId, courseId);
        }

        if (lessonProgressLoadPort.isLessonCompleted(userId, lessonId)) {
            return;
        }

        var progress = LessonProgress.create(userId, lessonId);
        lessonProgressSavePort.save(progress);
    }
}
