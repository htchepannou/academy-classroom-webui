package io.tchepannou.www.academy.classroom.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlHealthCheck implements HealthIndicator {
    private final String url;

    private final int connectTimeout;

    public UrlHealthCheck(final String url, final int connectTimeout){
        this.url = url;
        this.connectTimeout = connectTimeout;
    }

    @Override
    public Health health() {
        final long start = System.currentTimeMillis();
        try {

            final int errorCode = check();
            final long latency = System.currentTimeMillis() - start;

            if (errorCode == 200) {
                return Health
                        .up()
                        .withDetail("url", url)
                        .withDetail("latency", latency)
                        .build();
            } else {
                return Health.down()
                        .withDetail("url", url)
                        .withDetail("HTTP status", errorCode)
                        .withDetail("latency", latency)
                        .build();
            }

        } catch (final IOException ex) {
            final long latency = System.currentTimeMillis() - start;
            return Health
                    .down()
                    .withDetail("url", url)
                    .withDetail("latency", latency)
                    .withException(ex)
                    .build();
        }
    }

    private int check() throws IOException {
        final HttpURLConnection cnn = (HttpURLConnection) new URL(url).openConnection();
        try {
            cnn.setConnectTimeout(connectTimeout);
            cnn.connect();
            return cnn.getResponseCode();
        } finally {
            cnn.disconnect();
        }
    }

}
