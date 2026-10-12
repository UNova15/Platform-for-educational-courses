package kira.course.adapter.out.persistence;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration("courseMigration")
@RequiredArgsConstructor
class DatabaseMigrationConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void migration() {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas("course")
                .locations("classpath:db/migration/course")
                .load()
                .migrate();
    }
}
