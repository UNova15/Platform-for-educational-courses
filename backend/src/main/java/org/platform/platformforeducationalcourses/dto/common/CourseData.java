package org.platform.platformforeducationalcourses.dto.common;

import java.util.List;
import refactor.course.domain.course.Course;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.test.Test;

public record CourseData(Course course, List<CourseModule> modules, List<Lesson> lessons, List<Test> tests) {}
