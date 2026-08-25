package org.platform.platformforeducationalcourses.mapper;

import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import refactor.course.application.port.in.test.create.TestCreateCommand;
import refactor.course.application.port.in.test.create.TestCreateResult;
import refactor.course.application.port.in.test.update.TestUpdateCommand;
import refactor.course.domain.internal.test.Test;
import org.platform.platformforeducationalcourses.dto.test.*;
import refactor.course.application.port.in.test.query.TestQueryResult;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestQuestion;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;

@Mapper(uses = {QuestionMapper.class})
public interface TestMapper {
    TestCreateDto toTestCreateDto(TestCreateCommand request);

    TestCreateResult toTestCreateResponse(Test test);

    TestUpdateCommand toTestUpdateDto(TestUpdateRequest updateRequest, long testId, long moduleId);

    TestQueryResult toTestFindResponse(Test test);

    StudentTestFindResponse toStudentTestFindResponse(
            Test test, LocalDateTime completedAt, LocalDateTime startedAt, Integer score);

    TestPostDto toTestPostDto(TestPostRequest testPostRequest);

    @Mapping(source = "testQuestions", target = "questions")
    TestReview toTestAttempt(Test test, List<TestQuestion> testQuestions);
}
