package refactor.progress.domain.enrollment;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.domain.DomainValidationException;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.User;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Enrollment {
    private final Id<User> userId;
    private final Id<Course> courseId;
    private final LocalDateTime createdAt;

    public static Enrollment createNew(Id<User> userId, Id<Course> courseId) {
        if (userId == null || courseId == null) {
            throw new DomainValidationException("Incorrect data to create enrollment");
        }

        return new Enrollment(userId, courseId, LocalDateTime.now());
    }
}
