package refactor.course.application.port.in.query.owned;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class FullTestView {
    private final long id;

    private final long moduleId;
    private final long teacherId;
    private final String title;
    private final String description;
    private final int orderIndex;

    @Setter
    private List<Question> questions;

    @Getter
    @Accessors(fluent = true)
    @RequiredArgsConstructor
    public static class Question {
        private final long id;
        private final long testId;
        private final String question;
        private final int orderIndex;

        @Setter
        private List<Option> options;
    }

    public record Option(long id, long questionId, String option, boolean isCorrect) {}
}
