package kira.course.api;

import java.util.Map;
import java.util.Set;

public record CourseAnswerKey(long testId, Map<Long, Set<Long>> answerOptionIdsByQuestionId) {}
