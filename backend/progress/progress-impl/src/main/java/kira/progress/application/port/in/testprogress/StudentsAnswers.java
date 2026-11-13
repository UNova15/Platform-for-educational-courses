package kira.progress.application.port.in.testprogress;

import java.util.List;

public record StudentsAnswers(long questionId, List<Long> optionsIds) {}
