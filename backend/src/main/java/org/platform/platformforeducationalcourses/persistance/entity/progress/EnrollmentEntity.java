package org.platform.platformforeducationalcourses.persistance.entity.progress;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("enrollments")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrollmentEntity {
    @Id
    private Long id;

    private final long userId;
    private final long courseId;
    private final LocalDateTime createdAt;
}
