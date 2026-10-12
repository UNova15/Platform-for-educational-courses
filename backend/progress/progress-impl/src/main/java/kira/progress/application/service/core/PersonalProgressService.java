package kira.progress.application.service.core;

import common.domain.Id;
import java.util.List;

import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import kira.progress.application.port.in.query.personalprogress.PersonalProgressUseCase;
import kira.progress.application.port.in.query.personalprogress.ProgressSummary;
import kira.progress.application.port.in.query.personalprogress.TestProgressSummary;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.application.port.out.external.CourseStructurePort;
import kira.progress.application.port.out.external.ModuleStructure;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.query.PersonalProgressQueryPort;
import kira.progress.application.port.out.persistance.query.TestAnswersViewQueryPort;
import kira.progress.application.service.exception.ProgressResourceName;
import kira.progress.domain.markers.CourseModule;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .findStudentsModuleStructureById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ProgressResourceName.MODULE, moduleId));

        if (!enrollmentLoadPort.isEnrollmentExist(studentId, moduleStructure.courseId())) {
            throw new ResourceAccessDeniedException(ProgressResourceName.COURSE, studentId, moduleStructure.courseId());
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
                .orElseThrow(() -> new ResourceNotFoundException(ProgressResourceName.TEST, testId));
    }
}
