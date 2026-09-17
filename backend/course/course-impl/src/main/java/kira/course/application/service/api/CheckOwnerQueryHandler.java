package kira.course.application.service.api;

import common.domain.Id;
import kira.course.api.CheckOwnerQuery;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckOwnerQueryHandler implements CheckOwnerQuery {
    private final CourseAccessPort accessPort;

    @Override
    public boolean isTeacherCourseOwner(long teacherId, long courseId) {
        Id<User> userId = Id.of(teacherId);
        Id<Course> mappedCourseId = Id.of(courseId);

        return accessPort.isCourseOwner(userId, mappedCourseId);
    }

    @Override
    public boolean isTeacherLessonOwner(long teacherId, long lessonId) {
        Id<User> userId = Id.of(teacherId);
        Id<Lesson> mappedLessonId = Id.of(lessonId);

        return accessPort.isLessonOwner(userId, mappedLessonId);
    }

    @Override
    public boolean isTeacherTestOwner(long teacherId, long testId) {
        Id<User> userId = Id.of(teacherId);
        Id<Test> mappedTestId = Id.of(testId);

        return accessPort.isTestOwner(userId, mappedTestId);
    }
}
