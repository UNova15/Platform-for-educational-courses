package kira.course.application.service;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.course.application.exceptions.CourseExceptionCode;
import kira.course.application.port.in.query.owned.*;
import kira.course.application.port.out.persistance.query.OwnedCoursesQueryPort;
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
public class OwnedCoursesQueryService implements OwnedCoursesQueryUseCase {
    private final OwnedCoursesQueryPort queryPort;

    @Override
    public List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    @Override
    public TeacherCourseView findTeachersCourseById(Id<User> teacherId, Id<Course> courseId) {
        TeacherCourseView view = queryPort
                .findTeachersCourseById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.COURSE_NOT_FOUND_EXCEPTION, Course.class, courseId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessException(
                    CourseExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, teacherId, courseId);
        }

        return view;
    }

    @Override
    public TeachersModuleView findTeachersModuleById(Id<User> teacherId, Id<CourseModule> moduleId) {
        TeachersModuleView view = queryPort
                .findTeachersModuleById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessException(
                    CourseExceptionCode.MODULE_ACCESS_EXCEPTION, CourseModule.class, teacherId, moduleId);
        }

        return view;
    }

    @Override
    public TeachersLessonView findTeachersLessonById(Id<User> teacherId, Id<Lesson> lessonId) {
        TeachersLessonView view = queryPort
                .findTeachersLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.LESSON_NOT_FOUND_EXCEPTION, Lesson.class, lessonId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessException(
                    CourseExceptionCode.LESSON_ACCESS_EXCEPTION, Lesson.class, teacherId, lessonId);
        }

        return view;
    }

    @Override
    public FullTestView findFullTestById(Id<User> teacherId, Id<Test> testId) {
        FullTestView view = queryPort
                .findFullTestById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.TEST_NOT_FOUND_EXCEPTION, Test.class, testId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessException(CourseExceptionCode.TEST_ACCESS_EXCEPTION, Test.class, teacherId, testId);
        }

        return view;
    }
}
