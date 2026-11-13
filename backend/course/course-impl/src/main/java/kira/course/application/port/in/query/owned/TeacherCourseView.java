package kira.course.application.port.in.query.owned;

import java.time.LocalDateTime;
import java.util.List;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.course.Tag;
import kira.course.domain.shared.CourseContentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class TeacherCourseView {
    private final long id;
    private final long teacherId;
    private final Tag tag;
    private final LocalDateTime createdAt;
    private final String title;
    private final String description;
    private final CourseStatus status;

    @Setter
    private List<Module> modules;

    public record Module(long id, int orderIndex, String title, String description, CourseContentStatus status) {}
}
