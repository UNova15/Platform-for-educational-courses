package kira.course.application.port.out.external;

import common.domain.Id;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;

public interface TestCompletionCheckPort {
    boolean isTestCompleted(Id<User> studentId, Id<Test> testId);

    boolean isStudentSolvingTest(Id<User> studentId, Id<Test> testId);
}
