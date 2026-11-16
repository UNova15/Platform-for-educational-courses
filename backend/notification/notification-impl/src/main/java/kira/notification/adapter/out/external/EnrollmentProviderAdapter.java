package kira.notification.adapter.out.external;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.application.port.out.external.EnrollmentProviderPort;
import kira.notification.domain.Course;
import kira.notification.domain.User;
import kira.progress.api.EnrollmentProviderQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentProviderAdapter implements EnrollmentProviderPort {
    private final EnrollmentProviderQuery enrollmentProviderQuery;

    @Override
    public List<Id<User>> findEnrolledUsers(Id<Course> courseId) {
        List<Long> studentIds = enrollmentProviderQuery.findEnrolledUsers(courseId.value());
        return studentIds.stream().map(Id::<User>of).toList();
    }
}
