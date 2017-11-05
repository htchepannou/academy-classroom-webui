package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.academy.user.client.auth.AuthResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.backend.AuthBackend")
public class AuthBackend extends Backend{
    public AuthResponse findSessionByToken(final String token) {
        final String uri = String.format("%s/access_token/%s", getUrl(), token);
        return restClient.get(uri, AuthResponse.class).getBody();
    }
}
