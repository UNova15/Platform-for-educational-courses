package kira.course.application.service.external;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.api.CourseProviderQuery;
import kira.course.application.port.out.persistance.query.CourseProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseProviderService implements CourseProviderQuery {
    private final CourseProviderPort courseProviderPort;

    @Override
    public Optional<CourseInfo> getCourseInfo(long courseId) {
        return courseProviderPort.getCourseInfo(Id.of(courseId));
    }

    @Override
    public Optional<TestInfo> getTestInfo(long testId) {
        return courseProviderPort.getTestInfo(Id.of(testId));
    }
}
