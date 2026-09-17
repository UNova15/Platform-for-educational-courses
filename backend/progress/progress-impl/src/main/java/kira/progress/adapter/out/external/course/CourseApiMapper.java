package kira.progress.adapter.out.external.course;

import common.domain.Id;
import kira.course.api.CourseAnswerKey;
import kira.course.api.CourseModuleStructure;
import kira.progress.application.port.out.external.ModuleStructure;
import kira.progress.domain.markers.*;
import kira.progress.domain.testprogress.valueobject.AnswerKey;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseApiMapper {

    public AnswerKey toAnswerKey(CourseAnswerKey courseAnswerKey) {
        Id<Test> mappedTestId = Id.of(courseAnswerKey.testId());

        Map<Id<Question>, Set<Id<AnswerOption>>> optionIdsByQuestionId =
                courseAnswerKey.answerOptionIdsByQuestionId().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> Id.of(entry.getKey()),
                                entry -> entry.getValue().stream()
                                        .map(Id::<AnswerOption>of)
                                        .collect(Collectors.toSet())));

        return AnswerKey.createNew(mappedTestId, optionIdsByQuestionId);
    }

    public ModuleStructure toModuleStructure(CourseModuleStructure structure) {
        Id<Course> courseId = Id.of(structure.courseId());

        Set<Id<Lesson>> lessonsIds =
                structure.lessonsIds().stream().map(Id::<Lesson>of).collect(Collectors.toSet());
        Set<Id<Test>> testsIds = structure.testsIds().stream().map(Id::<Test>of).collect(Collectors.toSet());

        return new ModuleStructure(courseId, lessonsIds, testsIds);
    }
}
