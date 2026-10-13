package kira.course.application.service.external;

import common.domain.entity.Id;
import java.util.Optional;
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

@Service
@RequiredArgsConstructor
class CourseStructureQueryHandler implements CourseStructureQuery, CourseExistQuery {
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
    public Optional<CourseModuleStructure> findStudentsModuleStructureById(long moduleId) {
        Id<CourseModule> mappedModuleId = Id.of(moduleId);

        return queryPort.findStudentsModuleStructureById(mappedModuleId);
    }

    @Override
    public boolean isExistCourse(long courseId) {
        Id<Course> mappedCourseId = Id.of(courseId);

        return loadPort.isExist(mappedCourseId);
    }
}
