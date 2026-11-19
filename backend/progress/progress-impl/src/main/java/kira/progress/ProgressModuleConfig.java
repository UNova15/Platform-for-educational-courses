package kira.progress;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan(basePackages = "kira.progress")
@EnableJdbcRepositories(basePackages = "kira.progress.adapter.out.persistence")
public class ProgressModuleConfig {}
