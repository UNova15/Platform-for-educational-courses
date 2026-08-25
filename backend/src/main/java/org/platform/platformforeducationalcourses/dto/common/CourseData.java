package org.platform.platformforeducationalcourses.dto.common;

import java.util.List;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.internal.lesson.Lesson;
import refactor.course.domain.internal.test.Test;

public record CourseData(Course course, List<CourseModule> modules, List<Lesson> lessons, List<Test> tests) {}
