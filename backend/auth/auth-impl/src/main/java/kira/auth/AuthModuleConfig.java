package kira.auth;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan(basePackages = "kira.auth")
@EnableJdbcRepositories(basePackages = "kira.auth.adapter.out.persistence")
public class AuthModuleConfig {}
