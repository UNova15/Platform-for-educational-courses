package kira.course.application.service;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.course.application.exceptions.CourseExceptionCode;
import kira.course.application.exceptions.TestNotStartedException;
import kira.course.application.port.in.query.learning.*;
import kira.course.application.port.out.external.EnrollmentCheckPort;
import kira.course.application.port.out.external.TestCompletionCheckPort;
import kira.course.application.port.out.external.UserEnrollmentProviderPort;
import kira.course.application.port.out.persistance.query.LearningQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningQueryService implements LearningQueryUseCase {
    private final LearningQueryPort learningQueryPort;
    private final EnrollmentCheckPort enrollmentCheckPort;
    private final UserEnrollmentProviderPort enrollmentCoursesPort;
    private final TestCompletionCheckPort testCompletionCheckPort;

    @Override
    public List<UserCourseView> findCoursesThatUsersIsEnrolledIn(Id<User> requesterId) {
        List<Id<Course>> coursesIds = enrollmentCoursesPort.findCoursesIdsThatUsersIsEnrolledIn(requesterId);

        if (coursesIds.isEmpty()) {
            return List.of();
        }

        return learningQueryPort.findCoursesThatUsersIsEnrolledIn(coursesIds);
    }

    @Override
    public StudentsCourseView findStudentsCourse(Id<User> requesterId, Id<Course> courseId) {
        StudentsCourseView view = learningQueryPort
                .findStudentsCourseViewById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.COURSE_NOT_FOUND_EXCEPTION, Course.class, courseId) {});

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {

            throw new ResourceAccessException(
                    CourseExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, requesterId, courseId);
        }

        return view;
    }

    @Override
    public StudentsModuleView findStudentsModule(Id<User> requesterId, Id<CourseModule> moduleId) {
        StudentsModuleView view = learningQueryPort
                .findStudentsModuleViewById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {

            throw new ResourceAccessException(
                    CourseExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, requesterId, courseId);
        }

        return view;
    }

    @Override
    public StudentsLessonView findStudentsLesson(Id<User> requesterId, Id<Lesson> lessonId) {
        StudentsLessonView view = learningQueryPort
                .findStudentsLessonViewById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.LESSON_NOT_FOUND_EXCEPTION, Lesson.class, lessonId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, requesterId, courseId);
        }

        return view;
    }

    @Override
    public StudentsTestView findStudentsTest(Id<User> requesterId, Id<Test> testId) {
        StudentsTestView view = learningQueryPort
                .findStudentsTestView(testId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.TEST_NOT_FOUND_EXCEPTION, Test.class, testId));

        Id<Course> courseId = Id.of(view.courseId());

        if (!enrollmentCheckPort.isUserEnrolledInCourse(requesterId, courseId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, requesterId, courseId);
        }

        if (!testCompletionCheckPort.isTestCompleted(requesterId, testId)
                || !testCompletionCheckPort.isStudentSolvingTest(requesterId, testId)) {
            throw new TestNotStartedException(testId, requesterId);
        }
        return view;
    }
}
