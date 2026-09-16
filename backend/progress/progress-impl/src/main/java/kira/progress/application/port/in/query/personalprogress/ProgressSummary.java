package kira.progress.application.port.in.query.personalprogress;

import java.util.List;

public record ProgressSummary(List<LessonProgressSummary> lessonProgress,List<TestProgressSummary> testProgress) {}
