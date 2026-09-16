package kira.course.adapter.out.persistance.query.owned;

import common.domain.Id;
import kira.course.application.port.in.query.owned.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.course.application.port.out.persistance.query.OwnedCoursesQueryPort;

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
    public List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId) {
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
    public Optional<FullTestView> findFullTestById(Id<Test> testId) {
        Optional<FullTestView> view = testQueryRepository.findTeachersTestViewById(testId.value());

        view.ifPresent(test -> {
            List<FullTestView.Question> questions = testQueryRepository.findAllQuestionsInTestByTestId(test.id());

            if (questions.isEmpty()) {
                test.questions(List.of());
                return;
            }

            List<Long> questionIds =
                    questions.stream().map(FullTestView.Question::id).toList();

            List<FullTestView.Option> options = testQueryRepository.findAllOptionsInTestByQuestionIds(questionIds);

            Map<Long, List<FullTestView.Option>> optionsByQuestionId =
                    options.stream().collect(Collectors.groupingBy(FullTestView.Option::questionId));

            for (var question : questions) {
                List<FullTestView.Option> foundOptions = optionsByQuestionId.getOrDefault(question.id(), List.of());
                question.options(foundOptions);
            }

            test.questions(questions);
        });

        return view;
    }
}
