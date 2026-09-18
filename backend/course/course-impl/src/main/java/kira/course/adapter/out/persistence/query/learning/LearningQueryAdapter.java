package kira.course.adapter.out.persistence.query.learning;

import common.domain.Id;
import kira.course.application.port.in.query.learning.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.course.application.port.out.persistance.query.LearningQueryPort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//В случае роста сложности маппинга вынести его в отдельную фабрику
@Repository
@RequiredArgsConstructor
public class LearningQueryAdapter implements LearningQueryPort {
    private final LearningCourseQueryRepository courseQueryRepository;
    private final LearningModuleQueryRepository moduleQueryRepository;
    private final LearningLessonQueryRepository lessonQueryRepository;
    private final LearningTestQueryRepository testQueryRepository;

    @Override
    public List<UserCourseView> findCoursesThatUsersIsEnrolledIn(List<Id<Course>> ids) {
        List<Long> coursesIds = ids.stream().map(Id::value).toList();

        return courseQueryRepository.findEnrolledCoursesByCoursesIds(coursesIds);
    }

    @Override
    public Optional<StudentsCourseView> findStudentsCourseViewById(Id<Course> id) {
        Optional<StudentsCourseView> view = courseQueryRepository.findStudentsCourseViewById(id.value());

        view.ifPresent(course -> {
            List<StudentsCourseView.Module> modules =
                    moduleQueryRepository.findStudentsCourseModuleViewByCourseId(id.value());

            if (modules.isEmpty()) {
                return;
            }

            course.modules(modules);
        });

        return view;
    }

    @Override
    public Optional<StudentsModuleView> findStudentsModuleViewById(Id<CourseModule> id) {
        Optional<StudentsModuleView> view = moduleQueryRepository.findStudentsModuleView(id.value());

        view.ifPresent(module -> {
            List<StudentsModuleView.Lesson> lessons = lessonQueryRepository.findStudentsModuleLessonView(id.value());
            List<StudentsModuleView.Test> tests = testQueryRepository.findStudentsModuleTestView(id.value());

            module.lessons(lessons.isEmpty() ? List.of() : lessons);
            module.tests(tests.isEmpty() ? List.of() : tests);
        });

        return view;
    }

    @Override
    public Optional<StudentsLessonView> findStudentsLessonViewById(Id<Lesson> id) {
        return lessonQueryRepository.findStudentsLessonView(id.value());
    }

    @Override
    public Optional<StudentsTestView> findStudentsTestView(Id<Test> id) {
        Optional<StudentsTestView> view = testQueryRepository.findStudentsTestView(id.value());

        view.ifPresent(test -> {
            List<StudentsTestView.Question> questions = testQueryRepository.findStudentsTestQuestionsView(id.value());

            if (questions.isEmpty()) {
                test.questions(List.of());
                return;
            }
            List<Long> questionsIds =
                    questions.stream().map(StudentsTestView.Question::id).toList();

            List<StudentsTestView.AnswerOption> options = testQueryRepository.findStudentsTestOptionsView(questionsIds);

            Map<Long, List<StudentsTestView.AnswerOption>> optionsByQuestionId =
                    options.stream().collect(Collectors.groupingBy(StudentsTestView.AnswerOption::questionId));

            for (var question : questions) {
                question.options(optionsByQuestionId.getOrDefault(question.id(), List.of()));
            }

            test.questions(questions);
        });

        return view;
    }
}
