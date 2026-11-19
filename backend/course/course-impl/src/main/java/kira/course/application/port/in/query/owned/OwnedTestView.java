package kira.course.application.port.in.query.owned;

import java.util.List;
import kira.course.domain.shared.CourseContentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class OwnedTestView {
    private final long id;

    private final long moduleId;
    private final long teacherId;
    private final String title;
    private final String description;
    private final int orderIndex;
    private final CourseContentStatus status;

    @Setter
    private List<Question> questions;

    @Getter
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
