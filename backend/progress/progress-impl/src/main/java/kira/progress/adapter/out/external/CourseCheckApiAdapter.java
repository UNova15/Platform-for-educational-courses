package kira.progress.adapter.out.external;

import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.api.CheckCourseStatusQuery;
import kira.course.api.CheckOwnerQuery;
import kira.progress.application.port.out.external.CheckCourseOwnerPort;
import kira.progress.application.port.out.external.CheckCourseStatusPort;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.markers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CourseCheckApiAdapter implements CheckCourseOwnerPort, CheckCourseStatusPort {
    private final CheckOwnerQuery checkOwnerQuery;
    private final CheckCourseStatusQuery checkStatusQuery;

    @Override
    public boolean isCourseOwner(Id<User> teacherId, Id<Course> courseId) {
        return checkOwnerQuery.isTeacherCourseOwner(teacherId.value(), courseId.value());
    }

    @Override
    public boolean isLessonOwner(Id<User> teacherId, Id<Lesson> lessonId) {
        return checkOwnerQuery.isTeacherLessonOwner(teacherId.value(), lessonId.value());
    }

    @Override
    public boolean isTestOwner(Id<User> teacherId, Id<Test> testId) {
        return checkOwnerQuery.isTeacherTestOwner(teacherId.value(), testId.value());
    }

    @Override
    public boolean isTestPublished(Id<Test> testId) {
        return checkStatusQuery
                .isTestPublished(testId.value())
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.TEST, testId));
    }

    @Override
    public boolean isCoursePublished(Id<Course> courseId) {
        return checkStatusQuery
                .isCoursePublished(courseId.value())
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.COURSE, courseId));
    }

    @Override
    public boolean isLessonPublished(Id<Lesson> lessonId) {
        return checkStatusQuery
                .isLessonPublished(lessonId.value())
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.LESSON, lessonId));
    }

    @Override
    public boolean isModulePublished(Id<CourseModule> moduleId) {
        return checkStatusQuery
                .isModulePublished(moduleId.value())
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.MODULE, moduleId));
    }
}
