package course.application.port.in.query.learning;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import refactor.course.implementation.domain.course.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class StudentsCourseView {
    private final long id;
    private final long teacherId;
    private final Tag tag;
    private final LocalDateTime createdAt;
    private final String title;
    private final String description;

    @Setter
    private List<Module> modules;

    public record Module(String title, long id, int orderIndex) {}
}
