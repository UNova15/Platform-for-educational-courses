package kira.course.application.port.in.query.owned;

import java.time.LocalDateTime;

import kira.course.domain.course.CourseStatus;
import kira.course.domain.course.Tag;

public record OwnedCoursesView(long id, String title, String description, Tag tag, LocalDateTime createdAt,
                               CourseStatus status) {}
