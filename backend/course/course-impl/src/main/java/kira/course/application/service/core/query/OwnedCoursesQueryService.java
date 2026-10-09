package kira.course.application.service.core.query;

import common.domain.Id;
import java.util.List;
import java.util.Optional;

import common.domain.Result;
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
public class OwnedCoursesQueryService implements OwnedCoursesQueryUseCase {
    private final OwnedCoursesQueryPort queryPort;

    @Override
    public List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    @Override
    public Result<TeacherCourseView, CourseQueryError> findTeachersCourseById(Id<User> teacherId, Id<Course> courseId) {
        Optional<TeacherCourseView> loaded = queryPort.findTeachersCourseById(courseId);

        if (loaded.isEmpty()) {
            return Result.failure(CourseQueryError.RESOURCE_NOT_FOUND);
        }

        var view = loaded.get();

        if (view.teacherId() != teacherId.value()) {
            return Result.failure(CourseQueryError.ACCESS_DENIED);
        }

        return Result.success(view);
    }

    @Override
    public Result<TeachersModuleView, CourseQueryError> findTeachersModuleById(
            Id<User> teacherId, Id<CourseModule> moduleId) {
        Optional<TeachersModuleView> loaded = queryPort.findTeachersModuleById(moduleId);

        if (loaded.isEmpty()) {
            return Result.failure(CourseQueryError.RESOURCE_NOT_FOUND);
        }

        var view = loaded.get();

        if (view.teacherId() != teacherId.value()) {
            return Result.failure(CourseQueryError.ACCESS_DENIED);
        }

        return Result.success(view);
    }

    @Override
    public Result<TeachersLessonView, CourseQueryError> findTeachersLessonById(
            Id<User> teacherId, Id<Lesson> lessonId) {
        Optional<TeachersLessonView> loaded = queryPort.findTeachersLessonById(lessonId);

        if (loaded.isEmpty()) {
            return Result.failure(CourseQueryError.RESOURCE_NOT_FOUND);
        }

        var view = loaded.get();

        if (view.teacherId() != teacherId.value()) {
            return Result.failure(CourseQueryError.ACCESS_DENIED);
        }

        return Result.success(view);
    }

    @Override
    public Result<FullTestView, CourseQueryError> findFullTestById(Id<User> teacherId, Id<Test> testId) {
        Optional<FullTestView> loaded = queryPort.findFullTestById(testId);

        if (loaded.isEmpty()) {
            return Result.failure(CourseQueryError.RESOURCE_NOT_FOUND);
        }

        var view = loaded.get();

        if (view.teacherId() != teacherId.value()) {
            return Result.failure(CourseQueryError.ACCESS_DENIED);
        }

        return Result.success(view);
    }
}
