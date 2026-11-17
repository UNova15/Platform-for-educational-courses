package kira.notification;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "kira.notification")
public class NotificationModuleConfig {}
