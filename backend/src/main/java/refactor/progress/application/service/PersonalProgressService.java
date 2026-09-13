package refactor.progress.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.progress.application.exception.CourseAccessDenialException;
import refactor.progress.application.exception.TestAttemptNotFoundException;
import refactor.progress.application.port.in.query.TestAnswersView;
import refactor.progress.application.port.in.query.personalprogress.PersonalProgressUseCase;
import refactor.progress.application.port.in.query.personalprogress.ProgressSummary;
import refactor.progress.application.port.out.external.CourseStructurePort;
import refactor.progress.application.port.out.external.ModuleStructure;
import refactor.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import refactor.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import refactor.progress.application.port.out.persistance.query.personalprogress.PersonalProgressQueryPort;
import refactor.progress.application.port.in.query.personalprogress.TestProgressSummary;
import refactor.progress.application.port.out.persistance.query.shared.TestAnswersViewQueryPort;
import refactor.progress.domain.markers.CourseModule;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalProgressService implements PersonalProgressUseCase {
    private final CourseStructurePort courseStructurePort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final PersonalProgressQueryPort personalProgressQueryPort;
    private final TestAnswersViewQueryPort testAnswersViewQueryPort;

    @Override
    public ProgressSummary findStudentsProgressInModule(Id<User> studentId, Id<CourseModule> moduleId) {
        ModuleStructure moduleStructure = courseStructurePort
                .findModuleStructureById(moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId.value(), studentId.value()));

        if (!enrollmentLoadPort.isEnrollmentExist(studentId, moduleStructure.courseId())) {
            throw new CourseAccessDenialException(moduleStructure.courseId(), studentId);
        }

        List<LessonProgressSummary> lessonsProgress =
                personalProgressQueryPort.findCompletedLessonsIn(moduleStructure.lessonsIds(), studentId);

        List<TestProgressSummary> testProgress =
                personalProgressQueryPort.findCompletedTestAttemptsIn(moduleStructure.testsIds(), studentId);

        return new ProgressSummary(lessonsProgress, testProgress);
    }

    @Override
    public TestAnswersView findStudentsTestAttempt(Id<User> studentId, Id<Test> testId) {
        return testAnswersViewQueryPort
                .findTestAnswersViewByTestIdAndStudentId(studentId, testId)
                .orElseThrow(() -> new TestAttemptNotFoundException(studentId, testId));
    }
}
