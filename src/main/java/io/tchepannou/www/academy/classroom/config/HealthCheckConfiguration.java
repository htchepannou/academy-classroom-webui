package io.tchepannou.www.academy.classroom.config;

import io.tchepannou.www.academy.classroom.health.UrlHealthCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthCheckConfiguration {

    @Bean
    HealthIndicator academyBackendHealthCheck(@Value("${application.backend.AcademyBackend.url}") final String url) {
        return createUrlHealthCheck(url + "/health");
    }

    @Bean
    HealthIndicator userBackendHealthCheck(@Value("${application.backend.UserBackend.url}") final String url) {
        return createUrlHealthCheck(url + "/health");
    }

    private HealthIndicator createUrlHealthCheck(final String url){
        return new UrlHealthCheck(url , 15000);
    }
}
