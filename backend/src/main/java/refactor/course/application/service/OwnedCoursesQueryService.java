package refactor.course.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.access.LessonAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.access.TestAccessException;
import refactor.common.exception.domain.CourseNotFoundException;
import refactor.common.exception.domain.LessonNotFoundException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.common.exception.domain.TestNotFoundException;
import refactor.course.application.port.in.query.owned.*;
import refactor.course.application.port.out.persistance.query.OwnedCoursesQueryPort;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.markers.Account;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnedCoursesQueryService implements OwnedCoursesQueryUseCase {
    private final OwnedCoursesQueryPort queryPort;

    @Override
    public List<OwnedCoursesView> findTeachersCourses(Id<Account> teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    @Override
    public TeacherCourseView findTeachersCourseById(Id<Account> teacherId, Id<Course> courseId) {
        TeacherCourseView view = queryPort
                .findTeachersCourseById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));

        if (view.teacherId() != teacherId.value()) {
            throw new CourseAccessException(courseId, teacherId);
        }

        return view;
    }

    @Override
    public TeachersModuleView findTeachersModuleById(Id<Account> teacherId, Id<CourseModule> moduleId) {
        TeachersModuleView view = queryPort
                .findTeachersModuleById(moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId, teacherId));

        if (view.teacherId() != teacherId.value()) {
            throw new ModuleAccessException(moduleId, teacherId);
        }

        return view;
    }

    @Override
    public TeachersLessonView findTeachersLessonById(Id<Account> teacherId, Id<Lesson> lessonId) {
        TeachersLessonView view =
                queryPort.findTeachersLessonById(lessonId).orElseThrow(() -> new LessonNotFoundException(lessonId));

        if (view.teacherId() != teacherId.value()) {
            throw new LessonAccessException(lessonId, teacherId);
        }

        return view;
    }

    @Override
    public TeachersTestView findTeachersTestById(Id<Account> teacherId, Id<Test> testId) {
        TeachersTestView view =
                queryPort.findTeachersTestById(testId).orElseThrow(() -> new TestNotFoundException(testId));
        if (view.teacherId() != teacherId.value()) {
            throw new TestAccessException(testId, teacherId);
        }

        return view;
    }
}
