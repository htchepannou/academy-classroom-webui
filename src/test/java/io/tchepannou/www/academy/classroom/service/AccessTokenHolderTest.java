package io.tchepannou.www.academy.classroom.service;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenHolderTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @InjectMocks
    AccessTokenHolder holder;

    @Test
    public void shouldReturnAccessToken() throws Exception {
        when(request.getCookies()).thenReturn(new Cookie[]{
            new Cookie("foo", "bar"),
            new Cookie("guid", "123"),
        });

        assertThat(holder.get(request)).isEqualTo("123");
    }

    @Test
    public void shouldReturnNullWhenAccessTokenCookieNotAvaialable() throws Exception {
        when(request.getCookies()).thenReturn(new Cookie[]{
                new Cookie("foo", "bar"),
        });

        assertThat(holder.get(request)).isNull();
    }

    @Test
    public void shouldReturnNullWhenNoCookieAvaialable() throws Exception {
        when(request.getCookies()).thenReturn(null);

        assertThat(holder.get(request)).isNull();
    }

    @Test
    public void shouldSetAccessTokenCookie() throws Exception {
        // Given
        when(request.getParameter("guid")).thenReturn("123");

        // When
        holder.set("123", response);

        // Then
        ArgumentCaptor<Cookie> cookie = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookie.capture());

        assertThat(cookie.getValue().getName()).isEqualTo("guid");
        assertThat(cookie.getValue().getValue()).isEqualTo("123");
    }
}
