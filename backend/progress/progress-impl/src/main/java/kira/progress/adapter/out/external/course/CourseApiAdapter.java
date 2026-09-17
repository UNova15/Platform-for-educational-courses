package kira.progress.adapter.out.external.course;

import common.domain.Id;
import kira.course.api.CheckOwnerQuery;
import kira.course.api.CourseExistQuery;
import kira.course.api.CourseStructureQuery;
import kira.course.api.TestAnswerKeyQuery;
import kira.progress.application.port.out.external.*;
import kira.progress.domain.markers.*;
import kira.progress.domain.testprogress.valueobject.AnswerKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

// При разрастании класса на 200+ строк, разделить логически на меньшие адаптеры
@Component
@RequiredArgsConstructor
public class CourseApiAdapter
        implements CheckCourseOwnerPort, CourseStructurePort, CourseExistCheckPort, TestAnswerKeyProviderPort {
    private final CheckOwnerQuery checkOwnerQuery;
    private final CourseStructureQuery providerQuery;
    private final CourseExistQuery existQuery;
    private final TestAnswerKeyQuery testAnswerKeyQuery;

    private final CourseApiMapper mapper;

    @Override
    public boolean isTeacherCourseOwner(Id<User> teacherId, Id<Course> courseId) {
        return checkOwnerQuery.isTeacherCourseOwner(teacherId.value(), courseId.value());
    }

    @Override
    public boolean isTeacherLessonOwner(Id<User> teacherId, Id<Lesson> lessonId) {
        return checkOwnerQuery.isTeacherLessonOwner(teacherId.value(), lessonId.value());
    }

    @Override
    public boolean isTeacherTestOwner(Id<User> teacherId, Id<Test> testId) {
        return checkOwnerQuery.isTeacherTestOwner(teacherId.value(), testId.value());
    }

    @Override
    public Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId) {
        return providerQuery.findCourseIdByTestId(testId.value()).map(Id::of);
    }

    @Override
    public Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId) {
        return providerQuery.findCourseIdByLessonId(lessonId.value()).map(Id::of);
    }

    @Override
    public Optional<ModuleStructure> findModuleStructureById(Id<CourseModule> moduleId) {
        return providerQuery.findModuleStructureById(moduleId.value()).map(mapper::toModuleStructure);
    }

    @Override
    public boolean isCourseExist(Id<Course> courseId) {
        return existQuery.isExistCourse(courseId.value());
    }

    @Override
    public Optional<AnswerKey> findAnswerKeyByTestId(Id<Test> testId) {
        return testAnswerKeyQuery.findAnswerKeyByTestId(testId.value()).map(mapper::toAnswerKey);
    }
}
