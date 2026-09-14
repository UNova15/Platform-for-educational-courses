package course.application.port.in.query.owned;

import java.time.LocalDateTime;

import refactor.course.implementation.domain.course.Tag;

public record OwnedCoursesView(long id, String title, String description, Tag tag, LocalDateTime createdAt) {}
