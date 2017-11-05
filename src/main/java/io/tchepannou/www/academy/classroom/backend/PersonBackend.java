package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.academy.user.client.person.PersonResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.backend.PersonBackend")
public class PersonBackend extends Backend{
    public PersonResponse findPersonByRole(final Integer roleId) {
        final String uri = String.format("%s/roles/%s", getUrl(), roleId);
        return restClient.get(uri, PersonResponse.class).getBody();
    }
}
