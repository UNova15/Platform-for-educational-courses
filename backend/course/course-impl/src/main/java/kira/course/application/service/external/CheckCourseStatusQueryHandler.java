package kira.course.application.service.external;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.api.CheckCourseStatusQuery;
import kira.course.application.port.out.persistance.course.CourseCheckStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonCheckStatusPort;
import kira.course.application.port.out.persistance.module.ModuleCheckStatusPort;
import kira.course.application.port.out.persistance.test.TestCheckStatusPort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.shared.CourseContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CheckCourseStatusQueryHandler implements CheckCourseStatusQuery {
    private final ModuleCheckStatusPort moduleStatusPort;
    private final CourseCheckStatusPort courseStatusPort;
    private final TestCheckStatusPort testStatusPort;
    private final LessonCheckStatusPort lessonStatusPort;

    @Override
    public Optional<Boolean> isTestPublished(long testId) {
        return testStatusPort.findTestStatusById(Id.of(testId)).map(status -> status == CourseContentStatus.PUBLISHED);
    }

    @Override
    public Optional<Boolean> isCoursePublished(long courseId) {
        return courseStatusPort.findCourseStatusById(Id.of(courseId)).map(status -> status == CourseStatus.PUBLISHED);
    }

    @Override
    public Optional<Boolean> isLessonPublished(long lessonId) {
        return lessonStatusPort
                .findLessonStatusById(Id.of(lessonId))
                .map(status -> status == CourseContentStatus.PUBLISHED);
    }

    @Override
    public Optional<Boolean> isModulePublished(long moduleId) {
        return moduleStatusPort.findStatusById(Id.of(moduleId)).map(status -> status == CourseContentStatus.PUBLISHED);
    }
}
