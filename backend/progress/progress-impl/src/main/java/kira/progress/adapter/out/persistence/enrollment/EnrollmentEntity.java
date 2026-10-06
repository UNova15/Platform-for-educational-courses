package kira.progress.adapter.out.persistence.enrollment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("progress.enrollments")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class EnrollmentEntity {

    record EnrollmentKey(long userId, long courseId) {}

    @Id
    private final EnrollmentKey id;

    private final LocalDateTime createdAt;
}
