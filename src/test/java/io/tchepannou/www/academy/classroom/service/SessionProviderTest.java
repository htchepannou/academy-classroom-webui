package io.tchepannou.www.academy.classroom.service;

import io.tchepannou.www.academy.classroom.backend.user.AuthResponse;
import io.tchepannou.www.academy.classroom.backend.user.SessionDto;
import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.backend.user.UserException;
import io.tchepannou.www.academy.classroom.model.SessionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionProviderTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    private UserBackend userBackend;

    @Mock
    private AcademyMapper mapper;

    @InjectMocks
    SessionProvider provider;

    @Test
    public void getAccessTokenShouldReturnAToken() throws Exception {
        when(request.getCookies()).thenReturn(new Cookie[]{
            createCookie("foo", "bar"),
            createCookie("guid", "123"),
        });

        assertThat(provider.getAccessToken(request)).isEqualTo("123");
    }

    @Test
    public void getAccessTokenShouldReturnNullWhenCookieNotAvaialable() throws Exception {
        when(request.getCookies()).thenReturn(new Cookie[]{
                createCookie("foo", "bar"),
        });

        assertThat(provider.getAccessToken(request)).isNull();
    }

    @Test
    public void getAccessTokenShouldReturnNullWhenNoCookieAvaialable() throws Exception {
        when(request.getCookies()).thenReturn(null);

        assertThat(provider.getAccessToken(request)).isNull();
    }

    @Test
    public void setAccessTokenShouldAddAccessTokenCookie() throws Exception {
        // Given
        when(request.getParameter("guid")).thenReturn("123");

        // When
        provider.setAccessToken("123", request, response);

        // Then
        ArgumentCaptor<Cookie> cookie = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookie.capture());

        assertThat(cookie.getValue().getName()).isEqualTo("guid");
        assertThat(cookie.getValue().getValue()).isEqualTo("123");
        assertThat(cookie.getValue().getPath()).isEqualTo("/");
        assertThat(cookie.getValue().getVersion()).isEqualTo(1);
    }

    @Test
    public void setAccessTokenShouldUpdateCookieVersion() throws Exception {
        // Given
        when(request.getCookies()).thenReturn(new Cookie[]{
                createCookie("foo", "bar"),
                createCookie("guid", "----", "/", 12),
        });

        when(request.getParameter("guid")).thenReturn("123");

        // When
        provider.setAccessToken("123", request, response);

        // Then
        ArgumentCaptor<Cookie> cookie = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookie.capture());

        assertThat(cookie.getValue().getName()).isEqualTo("guid");
        assertThat(cookie.getValue().getValue()).isEqualTo("123");
        assertThat(cookie.getValue().getPath()).isEqualTo("/");
        assertThat(cookie.getValue().getVersion()).isEqualTo(13);
    }

    @Test
    public void getCurrentSessionShouldReturnEmptyIfNoAccessToken(){
        assertThat(provider.getCurrentSession(request)).isEmpty();
    }

    @Test
    public void getCurrentSessionShouldReturnEmptyIfAccessTokenInvalid(){
        when(request.getCookies()).thenReturn(new Cookie[]{
                createCookie("foo", "bar"),
                createCookie("guid", "123"),
        });

        when(userBackend.findSessionByToken("123")).thenThrow(new UserException(404, "test", new RuntimeException()));

        assertThat(provider.getCurrentSession(request)).isEmpty();
    }


    @Test
    public void getCurrentSessionShouldReturnSessionIfTokenValid(){
        // Given
        when(request.getCookies()).thenReturn(new Cookie[]{
                createCookie("guid", "123"),
        });

        final AuthResponse response = new AuthResponse();
        response.setSession(new SessionDto());
        when(userBackend.findSessionByToken("123")).thenReturn(response);

        final SessionModel session = new SessionModel();
        when(mapper.toSessionModel(any())).thenReturn(session);

        // When
        final Optional<SessionModel> result = provider.getCurrentSession(request);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(session);
    }

    private Cookie createCookie(String name, String value){
        return createCookie(name, value, "/", 1);
    }
    private Cookie createCookie(String name, String value, String path, int version){
        final Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setVersion(version);
        return cookie;
    }
}
