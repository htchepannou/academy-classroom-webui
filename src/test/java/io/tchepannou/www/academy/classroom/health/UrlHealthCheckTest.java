package io.tchepannou.www.academy.classroom.health;

import io.tchepannou.www.academy.support.jetty.HandlerStub;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlHealthCheckTest {

    private Server server;

    @After
    public void tearDown() throws Exception {
        if (server != null){
            stop(server);
        }
    }

    @Test
    public void shouldBeUp() throws Exception {
        final int port = 18999;
        server = start(port);

        final String url = "http://localhost:" + port + "/echo";
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


    private Server start(int port) throws Exception{
        final Server server = new Server(port);
        server.setHandler(new HandlerStub());
        server.start();

        return server;
    }

    private void stop(final Server server) throws Exception {
        server.stop();
    }
}
