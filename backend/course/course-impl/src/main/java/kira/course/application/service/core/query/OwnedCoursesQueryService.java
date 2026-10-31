package kira.course.application.service.core.query;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import java.util.List;
import kira.course.application.port.in.query.owned.*;
import kira.course.application.port.out.persistance.query.OwnedCoursesQueryPort;
import kira.course.domain.CourseAggregateType;
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
    public List<OwnedCoursesView> getOwnedCourses(Id<User> teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    @Override
    public TeacherCourseView getOwnedCourseUsingId(Id<User> teacherId, Id<Course> courseId) {
        TeacherCourseView view = queryPort
                .findOwnedCourseById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.COURSE, courseId));

        if (view.getTeacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseAggregateType.COURSE, teacherId, courseId);
        }

        return view;
    }

    @Override
    public OwnedModuleView getOwnedModule(Id<User> teacherId, Id<CourseModule> moduleId) {
        OwnedModuleView view = queryPort
                .findOwnedModuleById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.MODULE, moduleId));

        if (view.getTeacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseAggregateType.MODULE, teacherId, moduleId);
        }

        return view;
    }

    @Override
    public TeachersLessonView getOwnedLesson(Id<User> teacherId, Id<Lesson> lessonId) {
        TeachersLessonView view = queryPort
                .findOwnedLessonById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.LESSON, lessonId));

        if (view.teacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseAggregateType.LESSON, teacherId, lessonId);
        }

        return view;
    }

    @Override
    public OwnedTestView getOwnedTest(Id<User> teacherId, Id<Test> testId) {
        OwnedTestView view = queryPort
                .findOwnedTestById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.TEST, testId));

        if (view.getTeacherId() != teacherId.value()) {
            throw new ResourceAccessDeniedException(CourseAggregateType.TEST, testId, teacherId);
        }

        return view;
    }
}
