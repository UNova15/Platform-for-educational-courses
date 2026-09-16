package kira.course.application.port.in.query.learning;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class StudentsModuleView {
    private final long id;
    private final long courseId;
    private final int orderIndex;
    private final String title;
    private final String description;

    @Setter
    private List<Lesson> lessons;

    @Setter
    private List<Test> tests;

    public record Lesson(String title, long id, long moduleId, int orderIndex, boolean mandatory) {}

    public record Test(String title, long id, long moduleId, int orderIndex) {}
}
