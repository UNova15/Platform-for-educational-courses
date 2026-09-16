package kira.course.application.port.in.query.owned;

import kira.course.domain.course.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class TeacherCourseView {
    private final long id;
    private final long teacherId;
    private final Tag tag;
    private final LocalDateTime createdAt;
    private final String title;
    private final String description;

    @Setter
    private List<Module> modules;

    public record Module(long id, int orderIndex, String title, String description) {}
}
