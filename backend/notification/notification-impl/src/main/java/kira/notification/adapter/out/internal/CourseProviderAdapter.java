package kira.notification.adapter.out.internal;

import common.domain.entity.Id;
import kira.course.api.CourseProviderQuery;
import kira.notification.application.port.out.internal.CourseProviderPort;
import kira.notification.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseProviderAdapter implements CourseProviderPort {
    private final CourseProviderQuery courseProviderQuery;

    @Override
    public Optional<CourseDetails> getCourseDetails(Id<Course> courseId) {
        Optional<CourseProviderQuery.CourseInfo> info = courseProviderQuery.getCourseInfo(courseId.value());

        return info.map(value -> new CourseDetails(value.title(), Id.of(value.teacherId())));
    }
}
