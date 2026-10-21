package kira.progress.adapter.out.persistence.query.personalprogress;

import common.domain.entity.Id;
import java.util.*;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.Test;
import org.springframework.jdbc.core.ResultSetExtractor;

class PersonalProgressMapper {
    public static ResultSetExtractor<Optional<TestAnswersView>> extractAnswers(Id<Test> testId) {
        return rs -> {
            Map<Long, List<Long>> optionsIdsByQuestionId = new HashMap<>();

            if (!rs.next()) return Optional.empty();

            do {
                long questionId = rs.getLong("question_id");
                long optionId = rs.getLong("option_id");

                optionsIdsByQuestionId
                        .computeIfAbsent(questionId, id -> new ArrayList<>())
                        .add(optionId);
            } while (rs.next());

            List<TestAnswersView.QuestionAnswer> answers = optionsIdsByQuestionId.entrySet().stream()
                    .map(option -> new TestAnswersView.QuestionAnswer(option.getKey(), option.getValue()))
                    .toList();

            return Optional.of(new TestAnswersView(testId.value(), answers));
        };
    }
}
