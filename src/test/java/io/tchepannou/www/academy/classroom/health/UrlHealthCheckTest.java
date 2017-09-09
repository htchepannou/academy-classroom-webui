package io.tchepannou.www.academy.classroom.health;

import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlHealthCheckTest {

    @Test
    public void shouldBeUp() {
        final String url = "http://www.google.ca";
        final UrlHealthCheck healthCheck = new UrlHealthCheck(url, 1000);

        final Health result = healthCheck.health();

        assertThat(result.getStatus()).isEqualTo(Status.UP);
        assertThat(result.getDetails()).containsKey("latency");
        assertThat(result.getDetails()).containsEntry("url", url);
        assertThat(result.getDetails()).doesNotContainKeys("error");
    }

    @Test
    public void shouldBeDownWhenHttpStatusNot200() {
        final String url = "https://bdc-service.us-west-2.int.expedia.com/brandeddeals";
        final UrlHealthCheck healthCheck = new UrlHealthCheck(url, 1000);

        final Health result = healthCheck.health();

        assertThat(result.getStatus()).isEqualTo(Status.DOWN);
        assertThat(result.getDetails()).containsKey("latency");
        assertThat(result.getDetails()).containsEntry("url", url);
    }

    @Test
    public void shouldBeDownWhenInvalidUrl() {
        final String url = "https://www.xxx-google-xxx.ca";
        final UrlHealthCheck healthCheck = new UrlHealthCheck("https://www.xxx-google-xxx.ca", 1000);

        final Health result = healthCheck.health();

        assertThat(result.getStatus()).isEqualTo(Status.DOWN);
        assertThat(result.getDetails()).containsKey("latency");
        assertThat(result.getDetails()).containsEntry("url", url);
        assertThat(result.getDetails()).containsKey("error");
    }

}
