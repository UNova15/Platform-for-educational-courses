package org.platform.platformforeducationalcourses.dto.common;

import java.util.List;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.test.Test;

public record CourseData(Course course, List<CourseModule> modules, List<Lesson> lessons, List<Test> tests) {}
