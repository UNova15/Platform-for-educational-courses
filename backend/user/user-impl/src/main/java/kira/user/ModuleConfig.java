package kira.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan(basePackages = "kira.user")
@EnableJdbcRepositories(basePackages = "kira.user.adapter.out.persistence")
public class ModuleConfig {}
