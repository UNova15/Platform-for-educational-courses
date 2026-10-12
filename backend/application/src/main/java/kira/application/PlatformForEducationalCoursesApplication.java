package kira.application;

import kira.auth.ModuleConfig;
import kira.course.CourseModuleConfig;
import kira.infrastructure.InfrastructureModuleConfig;
import kira.progress.ProgressModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@Import({ModuleConfig.class, ProgressModuleConfig.class, CourseModuleConfig.class, InfrastructureModuleConfig.class})
public class PlatformForEducationalCoursesApplication {

    static void main(String[] args) {
        SpringApplication.run(PlatformForEducationalCoursesApplication.class, args);
    }
}
