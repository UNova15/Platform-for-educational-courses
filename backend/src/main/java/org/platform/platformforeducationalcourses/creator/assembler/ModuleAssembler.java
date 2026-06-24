package org.platform.platformforeducationalcourses.creator.assembler;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.StudentLessonFindResponse;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;
import org.platform.platformforeducationalcourses.mapper.ModuleMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModuleAssembler {
    private final TestAssembler testAssembler;
    private final LessonAssembler lessonAssembler;
    private final ModuleMapper moduleMapper;

    public List<CourseModuleFindResponse> createCourseModuleFindResponse(
            List<Lesson> lessons, List<Test> tests, List<CourseModule> modules) {
        // группировка уроков по id модуля
        Map<Long, List<Lesson>> lessonByModule = lessons.stream().collect(Collectors.groupingBy(Lesson::getModuleId));

        // группировка тестов по id модуля
        Map<Long, List<Test>> testByModule = tests.stream().collect(Collectors.groupingBy(Test::getModuleId));

        // сборка тестов и ответов и модулей в один объект
        return modules.stream()
                .map(module -> moduleMapper.toCourseModuleFindResponse(
                        module,
                        lessonByModule.getOrDefault(module.getId(), List.of()),
                        testByModule.getOrDefault(module.getId(), List.of())))
                .sorted(Comparator.comparing(CourseModuleFindResponse::orderIndex))
                .toList();
    }

    public List<StudentModuleFindResponse> createStudentModuleFindResponse(
            List<CourseModule> modules,
            List<Lesson> lessons,
            List<LessonProgress> lessonProgresses,
            List<Test> tests,
            List<TestSubmission> testSubmissions) {
        List<StudentLessonFindResponse> findLessons =
                lessonAssembler.createStudentLessonFindResponse(lessons, lessonProgresses);
        List<StudentTestFindResponse> mappedTests = testAssembler.createStudentTestFindResponse(tests, testSubmissions);
        // группировка тестов по id модуля
        Map<Long, List<StudentTestFindResponse>> testsOrderByModuleId =
                mappedTests.stream().collect(Collectors.groupingBy(StudentTestFindResponse::moduleId));
        // группировка уроков по id модуля
        Map<Long, List<StudentLessonFindResponse>> lessonOrderByModuleId =
                findLessons.stream().collect(Collectors.groupingBy(StudentLessonFindResponse::moduleId));

        return modules.stream()
                .map(module -> moduleMapper.toStudentModuleFindResponse(
                        module,
                        lessonOrderByModuleId.getOrDefault(module.getId(), List.of()),
                        testsOrderByModuleId.getOrDefault(module.getId(), List.of())))
                .toList();
    }
}
