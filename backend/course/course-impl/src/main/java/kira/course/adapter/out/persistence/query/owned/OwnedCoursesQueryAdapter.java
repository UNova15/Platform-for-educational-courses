package kira.course.adapter.out.persistence.query.owned;

import common.domain.entity.Id;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kira.course.application.port.in.query.owned.*;
import kira.course.application.port.out.persistance.query.OwnedCoursesQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OwnedCoursesQueryAdapter implements OwnedCoursesQueryPort {
    private final OwnedCourseQueryRepository courseRepository;
    private final OwnedModuleQueryRepository moduleQueryRepository;
    private final OwnedLessonQueryRepository lessonQueryRepository;
    private final OwnedTestQueryRepository testQueryRepository;

    @Override
    public List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId) {
        return courseRepository.findOwnedCoursesViewByTeacherId(teacherId.value());
    }

    @Override
    public Optional<TeacherCourseView> findOwnedCourseById(Id<Course> courseId) {
        Optional<TeacherCourseView> view = courseRepository.findOwnedCourseViewById(courseId.value());

        view.ifPresent(course -> {
            List<TeacherCourseView.Module> modules =
                    moduleQueryRepository.findOwnedCourseModuleViewByCourseId(courseId.value());
            course.setModules(modules);
        });

        return view;
    }

    @Override
    public Optional<OwnedModuleView> findOwnedModuleById(Id<CourseModule> moduleId) {
        Optional<OwnedModuleView> view = moduleQueryRepository.findOwnedViewById(moduleId.value());

        view.ifPresent(module -> {
            List<OwnedModuleView.Lesson> lessons =
                    lessonQueryRepository.findAllTeacherModuleLessonViewByModuleId(moduleId.value());
            List<OwnedModuleView.Test> tests =
                    testQueryRepository.findTeacherModuleTestViewByModuleId(moduleId.value());

            module.setLessons(lessons);
            module.setTests(tests);
        });

        return view;
    }

    @Override
    public Optional<TeachersLessonView> findOwnedLessonById(Id<Lesson> lessonId) {
        return lessonQueryRepository.findTeachersLessonViewById(lessonId.value());
    }

    // вынести сборку в отдельный класс маппер. Сначала запросить данные, затем в маппере собрать
    @Override
    public Optional<OwnedTestView> findOwnedTestById(Id<Test> testId) {
        Optional<OwnedTestView> view = testQueryRepository.findTeachersTestViewById(testId.value());

        view.ifPresent(test -> {
            List<OwnedTestView.Question> questions = testQueryRepository.findAllQuestionsInTestByTestId(test.getId());

            if (questions.isEmpty()) {
                test.setQuestions(List.of());
                return;
            }

            List<Long> questionIds =
                    questions.stream().map(OwnedTestView.Question::getId).toList();

            List<OwnedTestView.Option> options = testQueryRepository.findAllOptionsInTestByQuestionIds(questionIds);

            Map<Long, List<OwnedTestView.Option>> optionsByQuestionId =
                    options.stream().collect(Collectors.groupingBy(OwnedTestView.Option::questionId));

            for (var question : questions) {
                List<OwnedTestView.Option> foundOptions = optionsByQuestionId.getOrDefault(question.getId(), List.of());
                question.setOptions(foundOptions);
            }

            test.setQuestions(questions);
        });

        return view;
    }
}
