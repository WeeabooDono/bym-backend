package com.wd.bym.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EntityScan("com.wd.bym.core.domain")
@EnableJpaRepositories("com.wd.bym.core.repository")
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }
}
