package kira.progress.domain.enrollment;

import java.time.LocalDateTime;

import common.domain.Id;
import common.exception.DomainValidationException;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

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
