package kira.course.application.service.api;

import common.domain.Id;
import kira.course.api.CourseExistQuery;
import kira.course.api.CourseModuleStructure;
import kira.course.api.CourseStructureQuery;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.query.CourseStructureQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseStructureQueryHandler implements CourseStructureQuery, CourseExistQuery {
    private final CourseStructureQueryPort queryPort;
    private final CourseLoadPort loadPort;

    @Override
    public Optional<Long> findCourseIdByTestId(long testId) {
        Id<Test> mappedTestId = Id.of(testId);

        return queryPort.findCourseIdByTestId(mappedTestId).map(Id::value);
    }

    @Override
    public Optional<Long> findCourseIdByLessonId(long lessonId) {
        Id<Lesson> mappedLessonId = Id.of(lessonId);

        return queryPort.findCourseIdByLessonId(mappedLessonId).map(Id::value);
    }

    @Override
    public Optional<CourseModuleStructure> findModuleStructureById(long moduleId) {
        Id<CourseModule> mappedModuleId = Id.of(moduleId);

        return queryPort.findModuleStructureById(mappedModuleId);
    }

    @Override
    public boolean isExistCourse(long courseId) {
        Id<Course> mappedCourseId = Id.of(courseId);

        return loadPort.isExist(mappedCourseId);
    }
}
