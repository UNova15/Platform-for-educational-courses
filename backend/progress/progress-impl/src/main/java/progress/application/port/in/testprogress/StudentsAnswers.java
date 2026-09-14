package progress.application.port.in.testprogress;

import java.util.List;

//TODO можно ли в dto ядра оставлять примитивы?
public record StudentsAnswers(long questionId, List<Long> optionsIds) {}
