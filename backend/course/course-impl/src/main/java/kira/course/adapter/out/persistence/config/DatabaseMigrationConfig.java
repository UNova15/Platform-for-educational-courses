package kira.course.adapter.out.persistence.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("courseMigration")
@RequiredArgsConstructor
public class DatabaseMigrationConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void migration() {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas("course")
                .locations("classpath:db/migrations/course")
                .load()
                .migrate();
    }
}

