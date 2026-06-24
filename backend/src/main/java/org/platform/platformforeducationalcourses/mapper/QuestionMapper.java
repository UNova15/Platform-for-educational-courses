package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.platform.platformforeducationalcourses.dto.test.PostAnswerOptionRequest;
import org.platform.platformforeducationalcourses.dto.test.QuestionFindResponse;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.QuestionOption;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestQuestion;

@Mapper(uses = {AnswerQuestionMapper.class})
public interface QuestionMapper {
    QuestionFindResponse toCourseQuestionFindResponse(Question question);

    AnswerPostDto toAnswerPostDto(PostAnswerOptionRequest answerOptionRequest);

    // @Mapping(source = "options", target = "options")
    TestQuestion toTestQuestion(Question question, List<QuestionOption> options, long selectedId);
}
