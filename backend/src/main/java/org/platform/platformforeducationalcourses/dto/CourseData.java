package org.platform.platformforeducationalcourses.dto;

import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.course.Test;

import java.util.List;

public record CourseData(Course course, List<CourseModule> modules,
                         List<Lesson> lessons, List<Test> tests) {
}
