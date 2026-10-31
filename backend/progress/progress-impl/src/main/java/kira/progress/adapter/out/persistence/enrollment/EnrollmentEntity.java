package kira.progress.adapter.out.persistence.enrollment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "enrollments", schema = "progress")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class EnrollmentEntity {
    @Id
    private final Long id;

    private final long courseId;
    private final long userId;

    private final LocalDateTime createdAt;
}
