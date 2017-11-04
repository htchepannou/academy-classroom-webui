package io.tchepannou.www.academy.classroom.backend.user;

import io.tchepannou.www.academy.classroom.backend.Backend;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.backend.UserBackend")
public class UserBackend extends Backend{
    public AuthResponse findSessionByToken(final String token) {
        final String uri = String.format("%s/academy/v1/auth/access_token/%s", getUrl(), token);
        return restClient.get(uri, AuthResponse.class).getBody();
    }

    public PersonResponse findPersonByRole(final Integer roleId) throws UserException {
        final String uri = String.format("%s/academy/v1/persons/roles/%s", getUrl(), roleId);
        return restClient.get(uri, PersonResponse.class).getBody();
    }
}
