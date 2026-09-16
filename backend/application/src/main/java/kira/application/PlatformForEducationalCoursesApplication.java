package kira.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class PlatformForEducationalCoursesApplication {

    static void main(String[] args) {
        SpringApplication.run(PlatformForEducationalCoursesApplication.class, args);
    }
}