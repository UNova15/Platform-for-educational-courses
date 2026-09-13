package refactor.course.application.port.out.external;

import refactor.common.domain.Id;
import refactor.course.domain.markers.Account;
import refactor.course.domain.test.Test;

public interface TestCompletionCheckPort {
    boolean isTestCompleted(Id<Account> studentId, Id<Test> testId);

    boolean isStudentPassTest(Id<Account> studentId, Id<Test> testId);
}
