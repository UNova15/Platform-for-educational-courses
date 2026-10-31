package kira.auth.adapter.out.persistence;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration("authMigration")
@RequiredArgsConstructor
class DatabaseMigrationConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void migration() {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas("auth")
                .locations("classpath:db/migration/auth")
                .load()
                .migrate();
    }
}
