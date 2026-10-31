package kira.course.application.port.in.query.catalog;

import java.time.LocalDateTime;
import java.util.List;
import kira.course.domain.course.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class CourseInfo {
    private final long id;
    private final long teacherId;
    private final String title;
    private final String description;
    private final Tag tag;
    private final LocalDateTime createdAt;

    @Setter
    private List<Module> modules;

    public record Module(long id, long courseId, String title, String description, int orderIndex) {}
}
