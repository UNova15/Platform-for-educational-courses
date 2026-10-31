package kira.course;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan(basePackages = "kira.course")
@EnableJdbcRepositories(basePackages = "kira.course.adapter.out.persistence")
public class CourseModuleConfig {}
