package kira.notification.adapter.out.internal;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.api.CourseProviderQuery;
import kira.notification.application.port.out.internal.CourseProviderPort;
import kira.notification.domain.marker.Course;
import kira.notification.domain.marker.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseProviderAdapter implements CourseProviderPort {
    private final CourseProviderQuery courseProviderQuery;

    @Override
    public Optional<CourseDetails> getCourseDetails(Id<Course> courseId) {
        Optional<CourseProviderQuery.CourseInfo> info = courseProviderQuery.getCourseInfo(courseId.value());

        return info.map(value -> new CourseDetails(value.title(), Id.of(value.teacherId())));
    }

    @Override
    public Optional<TestDetails> getTestDetails(Id<Test> testId) {
        Optional<CourseProviderQuery.TestInfo> info = courseProviderQuery.getTestInfo(testId.value());

        return info.map(value -> new TestDetails(value.courseTitle(), value.testTitle(), Id.of(value.teacherId())));
    }
}
