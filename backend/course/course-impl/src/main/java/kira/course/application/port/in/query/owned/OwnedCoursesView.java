package kira.course.application.port.in.query.owned;

import kira.course.domain.course.Tag;

import java.time.LocalDateTime;

public record OwnedCoursesView(long id, String title, String description, Tag tag, LocalDateTime createdAt) {}
