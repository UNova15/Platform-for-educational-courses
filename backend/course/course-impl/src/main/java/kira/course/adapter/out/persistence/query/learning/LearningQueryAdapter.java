package kira.course.adapter.out.persistence.query.learning;

import common.domain.entity.Id;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kira.course.application.port.in.query.learning.*;
import kira.course.application.port.out.persistance.query.LearningQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// В случае роста сложности маппинга вынести его в отдельную фабрику
@Repository
@RequiredArgsConstructor
class LearningQueryAdapter implements LearningQueryPort {
    private final LearningCourseQueryRepository courseQueryRepository;
    private final LearningModuleQueryRepository moduleQueryRepository;
    private final LearningLessonQueryRepository lessonQueryRepository;
    private final LearningTestQueryRepository testQueryRepository;

    @Override
    public List<StudentCourseView> findCoursesThatUsersIsEnrolledIn(List<Id<Course>> ids) {
        List<Long> coursesIds = ids.stream().map(Id::value).toList();

        return courseQueryRepository.findEnrolledCoursesByCoursesIds(coursesIds);
    }

    @Override
    public Optional<StudentsCourseView> findCourseViewByIdAndStatus(Id<Course> id, CourseContentStatus status) {
        Optional<StudentsCourseView> view = courseQueryRepository.findStudentsCourseViewById(id.value());

        view.ifPresent(course -> {
            List<StudentsCourseView.Module> modules =
                    moduleQueryRepository.findStudentsCourseModuleViewByCourseId(id.value(), status.name());
            if (modules.isEmpty()) {
                return;
            }
            course.setModules(modules);
        });

        return view;
    }

    @Override
    public Optional<StudentsModuleView> findModuleViewByIdAndStatus(Id<CourseModule> id, CourseContentStatus status) {
        Optional<StudentsModuleView> view = moduleQueryRepository.findStudentsModuleView(id.value(), status.name());

        view.ifPresent(module -> {
            List<StudentsModuleView.Lesson> lessons =
                    lessonQueryRepository.findStudentsModuleLessonView(id.value(), status.name());
            List<StudentsModuleView.Test> tests =
                    testQueryRepository.findStudentsModuleTestView(id.value(), status.name());

            module.setLessons(lessons.isEmpty() ? List.of() : lessons);
            module.setTests(tests.isEmpty() ? List.of() : tests);
        });

        return view;
    }

    @Override
    public Optional<StudentsLessonView> findLessonViewByIdAndStatus(Id<Lesson> id, CourseContentStatus status) {
        return lessonQueryRepository.findStudentsLessonView(id.value(), status.name());
    }

    @Override
    public Optional<StudentsTestView> findTestViewByIdAndStatus(Id<Test> id, CourseContentStatus status) {
        Optional<StudentsTestView> view = testQueryRepository.findStudentsTestView(id.value(), status.name());

        view.ifPresent(test -> {
            List<StudentsTestView.Question> questions = testQueryRepository.findStudentsTestQuestionsView(id.value());
            if (questions.isEmpty()) {
                test.setQuestions(List.of());
                return;
            }
            List<Long> questionsIds =
                    questions.stream().map(StudentsTestView.Question::getId).toList();

            List<StudentsTestView.AnswerOption> options = testQueryRepository.findStudentsTestOptionsView(questionsIds);

            Map<Long, List<StudentsTestView.AnswerOption>> optionsByQuestionId =
                    options.stream().collect(Collectors.groupingBy(StudentsTestView.AnswerOption::questionId));

            for (var question : questions) {
                question.setOptions(optionsByQuestionId.getOrDefault(question.getId(), List.of()));
            }
            test.setQuestions(questions);
        });

        return view;
    }
}
