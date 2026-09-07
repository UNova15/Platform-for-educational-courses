package refactor.course.application.port.in.query.owned;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class TeachersModuleView {
    private final long id;

    private final long courseId;
    private final long teacherId;

    private final String title;
    private final String description;
    private final int orderIndex;

    @Setter
    private List<Lesson> lessons;
    @Setter
    private List<Test> tests;

    public record Lesson(long id, String title) {}

    public record Test(long id, String title) {}
}
