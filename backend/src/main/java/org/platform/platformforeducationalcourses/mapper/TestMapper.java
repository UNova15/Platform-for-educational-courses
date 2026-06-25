package org.platform.platformforeducationalcourses.mapper;

import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.dto.test.*;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestQuestion;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;

@Mapper(uses = {QuestionMapper.class})
public interface TestMapper {
    TestCreateDto toTestCreateDto(TestCreateRequest request);

    TestCreateResponse toTestCreateResponse(Test test);

    TestUpdateDto toTestUpdateDto(TestUpdateRequest updateRequest, long testId, long moduleId);

    TestFindResponse toTestFindResponse(Test test);

    StudentTestFindResponse toStudentTestFindResponse(
            Test test, LocalDateTime completedAt, LocalDateTime startedAt, Integer score);

    TestPostDto toTestPostDto(TestPostRequest testPostRequest);

    @Mapping(source = "testQuestions", target = "questions")
    TestReview toTestAttempt(Test test, List<TestQuestion> testQuestions);
}
