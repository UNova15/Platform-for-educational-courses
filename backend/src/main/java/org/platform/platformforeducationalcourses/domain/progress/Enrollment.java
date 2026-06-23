package org.platform.platformforeducationalcourses.domain.progress;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("enrollments")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Enrollment {
    @Id
    private Long id;
    private final long userId;
    private final long courseId;
    private final LocalDateTime createdAt;

    public static Enrollment createNew(long userId, long courseId) {
        if (userId < 0 || courseId < 0){
            throw new IllegalArgumentException("Incorrect data to create enrollment");
        }

        return new Enrollment(null,userId,courseId, LocalDateTime.now());
    }
}
