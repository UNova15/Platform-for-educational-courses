package kira.progress.adapter.out.persistence.config;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration("progressMigration")
@RequiredArgsConstructor
public class DatabaseMigrationConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void migration() {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas("progress")
                .locations("classpath:db/migrations/progress")
                .load()
                .migrate();
    }
}
