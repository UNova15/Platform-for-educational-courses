package kira.progress.application.service;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.progress.application.exceptions.ProgressExceptionCode;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import kira.progress.application.port.in.query.personalprogress.PersonalProgressUseCase;
import kira.progress.application.port.in.query.personalprogress.ProgressSummary;
import kira.progress.application.port.in.query.personalprogress.TestProgressSummary;
import kira.progress.application.port.out.external.CourseStructurePort;
import kira.progress.application.port.out.external.ModuleStructure;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.query.personalprogress.PersonalProgressQueryPort;
import kira.progress.application.port.out.persistance.query.shared.TestAnswersViewQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.CourseModule;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId));

        if (!enrollmentLoadPort.isEnrollmentExist(studentId, moduleStructure.courseId())) {
            throw new ResourceAccessException(
                    ProgressExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, studentId, moduleStructure.courseId());
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.TEST_ATTEMPT_NOT_FOUND_EXCEPTION, Test.class, testId));
    }
}
