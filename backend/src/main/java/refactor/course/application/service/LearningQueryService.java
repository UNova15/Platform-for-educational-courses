package refactor.course.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.domain.CourseNotFoundException;
import refactor.common.exception.domain.LessonNotFoundException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.common.exception.domain.TestNotFoundException;
import refactor.course.application.port.in.query.learning.*;
import refactor.course.application.port.out.external.EnrollmentCheckPort;
import refactor.course.application.port.out.external.UserEnrollmentProviderPort;
import refactor.course.application.port.out.persistance.query.LearningQueryPort;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.markers.Account;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningQueryService implements LearningQueryUseCase {
    private final LearningQueryPort learningQueryPort;
    private final EnrollmentCheckPort enrollmentCheckPort;
    private final UserEnrollmentProviderPort enrolmentCoursesPort;

    @Override
    public List<EnrolledCourse> findEnrolledCourses(Id<Account> requesterId) {
        List<Id<Course>> coursesIds = enrolmentCoursesPort.findEnrolledCourseIds(requesterId);

        if (coursesIds.isEmpty()) {
            return List.of();
        }

        return learningQueryPort.findEnrolledCoursesByIds(coursesIds);
    }

    @Override
    public StudentsCourseView findStudentsCourse(Id<Account> requesterId, Id<Course> courseId) {
        StudentsCourseView view = learningQueryPort
                .findStudentsCourseViewById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId, requesterId));

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new CourseAccessException(courseId, requesterId);
        }

        return view;
    }

    @Override
    public StudentsModuleView findStudentsModule(Id<Account> requesterId, Id<CourseModule> moduleId) {
        StudentsModuleView view = learningQueryPort
                .findStudentsModuleViewById(moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId, requesterId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new CourseAccessException(courseId, requesterId);
        }

        return view;
    }

    @Override
    public StudentsLessonView findStudentsLesson(Id<Account> requesterId, Id<Lesson> lessonId) {
        StudentsLessonView view = learningQueryPort
                .findStudentsLessonViewById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new CourseAccessException(courseId, requesterId);
        }

        return view;
    }

    @Override
    public StudentsTestView findStudentsTest(Id<Account> requesterId, Id<Test> testId) {
        StudentsTestView view =
                learningQueryPort.findStudentsTestView(testId).orElseThrow(() -> new TestNotFoundException(testId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new CourseAccessException(courseId, requesterId);
        }

        return view;
    }
}
