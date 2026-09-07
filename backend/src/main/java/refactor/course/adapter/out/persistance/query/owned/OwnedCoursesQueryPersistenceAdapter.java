package refactor.course.adapter.out.persistance.query.owned;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.common.domain.Id;
import refactor.course.application.port.in.query.owned.*;
import refactor.course.application.port.out.persistance.query.OwnedCoursesQueryPort;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.user.Account;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OwnedCoursesQueryPersistenceAdapter implements OwnedCoursesQueryPort {
    private final OwnedCourseQueryRepository courseRepository;
    private final OwnedModuleQueryRepository moduleQueryRepository;
    private final OwnedLessonQueryRepository lessonQueryRepository;
    private final OwnedTestQueryRepository testQueryRepository;

    @Override
    public List<OwnedCoursesView> findTeachersCourses(Id<Account> teacherId) {
        return courseRepository.findOwnedCoursesViewByTeacherId(teacherId.value());
    }

    @Override
    public Optional<TeacherCourseView> findTeachersCourseById(Id<Course> courseId) {
        Optional<TeacherCourseView> view = courseRepository.findTeacherCourseViewById(courseId.value());

        view.ifPresent(course -> {
            List<TeacherCourseView.Module> modules =
                    moduleQueryRepository.findTeacherCourseModuleViewByCourseId(courseId.value());
            course.modules(modules);
        });

        return view;
    }

    @Override
    public Optional<TeachersModuleView> findTeachersModuleById(Id<CourseModule> moduleId) {
        Optional<TeachersModuleView> view = moduleQueryRepository.findTeacherViewById(moduleId.value());

        view.ifPresent(module -> {
            List<TeachersModuleView.Lesson> lessons =
                    lessonQueryRepository.findAllTeacherModuleLessonViewByModuleId(moduleId.value());
            List<TeachersModuleView.Test> tests =
                    testQueryRepository.findTeacherModuleTestViewByModuleId(moduleId.value());

            module.lessons(lessons);
            module.tests(tests);
        });

        return view;
    }

    @Override
    public Optional<TeachersLessonView> findTeachersLessonById(Id<Lesson> lessonId) {
        return lessonQueryRepository.findTeachersLessonViewById(lessonId.value());
    }

    @Override
    public Optional<TeachersTestView> findTeachersTestById(Id<Test> testId) {
        Optional<TeachersTestView> view = testQueryRepository.findTeachersTestViewById(testId.value());

        view.ifPresent(test -> {
            List<TeachersTestView.Question> questions = testQueryRepository.findAllQuestionsInTestByTestId(test.id());

            if (questions.isEmpty()) {
                test.questions(List.of());
                return;
            }

            List<Long> questionIds =
                    questions.stream().map(TeachersTestView.Question::id).toList();

            List<TeachersTestView.Option> options = testQueryRepository.findAllOptionsInTestByQuestionIds(questionIds);

            Map<Long, List<TeachersTestView.Option>> optionsByQuestionId =
                    options.stream().collect(Collectors.groupingBy(TeachersTestView.Option::questionId));

            for (var question : questions) {
                List<TeachersTestView.Option> foundOptions = optionsByQuestionId.getOrDefault(question.id(), List.of());
                question.options(foundOptions);
            }

            test.questions(questions);
        });

        return view;
    }
}
