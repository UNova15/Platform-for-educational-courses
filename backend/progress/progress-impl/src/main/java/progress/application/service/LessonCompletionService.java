package progress.application.service;

import common.domain.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import progress.application.exception.CourseAccessDenialException;
import progress.application.exception.LessonNotFoundException;
import progress.application.port.in.lessonprogress.LessonCompletionUseCase;
import progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import progress.application.port.out.persistance.lessonprogress.LessonProgressLoadPort;
import progress.application.port.out.persistance.lessonprogress.LessonProgressSavePort;
import progress.domain.lessonprogress.LessonProgress;
import progress.domain.markers.Course;
import progress.domain.markers.Lesson;
import progress.domain.markers.User;

@Service
@RequiredArgsConstructor
public class LessonCompletionService implements LessonCompletionUseCase {
    private final LessonProviderPort lessonProviderPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final LessonProgressLoadPort lessonProgressLoadPort;
    private final LessonProgressSavePort lessonProgressSavePort;

    @Override
    public void completeLesson(Id<User> userId, Id<Lesson> lessonId) {
        Id<Course> courseId = lessonProviderPort
                .findCourseIdByLessonId(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new CourseAccessDenialException(courseId, userId);
        }

        if (lessonProgressLoadPort.isLessonCompleted(userId, lessonId)) {
            return;
        }

        LessonProgress progress = LessonProgress.createNew(userId, lessonId);
        lessonProgressSavePort.save(progress);
    }
}
