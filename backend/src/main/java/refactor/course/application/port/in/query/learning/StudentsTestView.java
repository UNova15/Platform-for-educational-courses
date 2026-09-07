package refactor.course.application.port.in.query.learning;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class StudentsTestView {
    private final long id;
    private final long moduleId;
    private final long courseId;
    private final int orderIndex;
    private final String title;
    private final String description;

    @Setter
    private List<Question> questions;

    @Getter
    @RequiredArgsConstructor
    public static class Question {

        private final long id;
        private final long testId;
        private final int orderIndex;
        private final String content;

        @Setter
        private List<AnswerOption> options;
    }

    public record AnswerOption(long id, long questionId, String option) {}
}
