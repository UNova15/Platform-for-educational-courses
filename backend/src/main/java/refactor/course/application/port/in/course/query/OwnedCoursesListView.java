package refactor.course.application.port.in.course.query;

import java.time.LocalDateTime;
import java.util.List;

import refactor.course.domain.course.Tag;

public record OwnedCoursesListView(List<Course> courses) {

    public record Course(long id, String title, String description, Tag tag, LocalDateTime createdAt) {}
}
