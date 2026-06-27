package org.platform.platformforeducationalcourses.domain.progress;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Enrollment {
    private Long id;

    private final long userId;
    private final long courseId;
    private final LocalDateTime createdAt;

    public static Enrollment createNew(long userId, long courseId) {
        if (userId < 0 || courseId < 0) {
            throw new IllegalArgumentException("Incorrect data to create enrollment");
        }

        return new Enrollment(null, userId, courseId, LocalDateTime.now());
    }
}
