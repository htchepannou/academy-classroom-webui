package io.tchepannou.www.academy.classroom.service;

import io.tchepannou.rest.HttpNotFoundException;
import io.tchepannou.rest.HttpUnauthorizedException;
import io.tchepannou.www.academy.classroom.backend.user.AuthResponse;
import io.tchepannou.www.academy.classroom.backend.user.SessionDto;
import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.exception.SessionException;
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
        provider.setAccessToken("hello", request, response);

        // Then
        ArgumentCaptor<Cookie> cook = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cook.capture());

        assertThat(cook.getValue().getName()).isEqualTo("guid");
        assertThat(cook.getValue().getValue()).isEqualTo("hello");
        assertThat(cook.getValue().getPath()).isEqualTo("/");
        assertThat(cook.getValue().getVersion()).isEqualTo(13);
    }

    @Test(expected = SessionException.class)
    public void getCurrentSessionShouldThrowExceptionIfNoAccessToken(){
        provider.getCurrentSession(request);
    }

    @Test(expected = SessionException.class)
    public void getCurrentSessionShouldThrowExceptionIfAccessTokenInvalid(){
        when(request.getCookies()).thenReturn(new Cookie[]{
                createCookie("foo", "bar"),
                createCookie("guid", "123"),
        });

        when(userBackend.findSessionByToken("123")).thenThrow(new HttpNotFoundException("failed"));

        provider.getCurrentSession(request);
    }


    @Test(expected = SessionException.class)
    public void getCurrentSessionShouldThrowExceptionIfUnauthorized(){
        when(request.getCookies()).thenReturn(new Cookie[]{
                createCookie("foo", "bar"),
                createCookie("guid", "123"),
        });

        when(userBackend.findSessionByToken("123")).thenThrow(new HttpUnauthorizedException("failed"));

        provider.getCurrentSession(request);
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
        final SessionModel result = provider.getCurrentSession(request);

        // Then
        assertThat(result).isEqualTo(session);
        verify(request).setAttribute(SessionProvider.ATTR_SESSION, result);
    }

    @Test
    public void getCurrentSessionShouldReturnCachedSession(){
        // Given
        final SessionModel session = new SessionModel();
        when(request.getAttribute(SessionProvider.ATTR_SESSION)).thenReturn(session);

        // When
        final SessionModel result = provider.getCurrentSession(request);

        // Then
        assertThat(result).isEqualTo(session);
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
