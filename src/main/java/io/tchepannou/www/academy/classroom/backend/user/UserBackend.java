package io.tchepannou.www.academy.classroom.backend.user;

import io.tchepannou.www.academy.classroom.backend.Backend;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
@ConfigurationProperties("application.backend.UserBackend")
public class UserBackend extends Backend{
    public AuthResponse findSessionByToken(final String token) throws UserException {
        try{
            final String uri = String.format("%s/academy/v1/auth/access_token/%s", getUrl(), token);
            return rest.getForEntity(uri, AuthResponse.class).getBody();
        } catch (RestClientResponseException e){
            throw new UserException(e.getRawStatusCode(), e.getResponseBodyAsString(), e);
        }
    }
}