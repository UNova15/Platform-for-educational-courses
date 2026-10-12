package kira.course.application.service.core.query;

import common.domain.Id;
import java.util.List;

import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.query.owned.*;
import kira.course.application.port.out.persistance.query.OwnedCoursesQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OwnedCoursesQueryService implements OwnedCoursesQueryUseCase {
    private final OwnedCoursesQueryPort queryPort;

    @Override
    public List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    @Override
    public TeacherCourseView findTeachersCourseById(Id<User> teacherId, Id<Course> courseId) {
        TeacherCourseView view = queryPort
                .findTeachersCourseById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, teacherId, courseId);
        }

        return view;
    }

    @Override
    public TeachersModuleView findTeachersModuleById(Id<User> teacherId, Id<CourseModule> moduleId) {
        TeachersModuleView view = queryPort
                .findTeachersModuleById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.MODULE, moduleId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseResourceName.MODULE, teacherId, moduleId);
        }

        return view;
    }

    @Override
    public TeachersLessonView findTeachersLessonById(Id<User> teacherId, Id<Lesson> lessonId) {
        TeachersLessonView view = queryPort
                .findTeachersLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.LESSON, lessonId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseResourceName.LESSON, teacherId, lessonId);
        }

        return view;
    }

    @Override
    public FullTestView findFullTestById(Id<User> teacherId, Id<Test> testId) {
        FullTestView view = queryPort
                .findFullTestById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.TEST, testId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseResourceName.TEST, testId, teacherId);
        }

        return view;
    }
}
