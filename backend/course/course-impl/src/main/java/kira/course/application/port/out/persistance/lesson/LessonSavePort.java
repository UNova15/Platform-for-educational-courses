package kira.course.application.port.out.persistance.lesson;

import java.util.List;
import kira.course.domain.lesson.Lesson;

public interface LessonSavePort {

    List<Lesson> saveAll(List<Lesson> lessons);

    Lesson save(Lesson lesson);
}
