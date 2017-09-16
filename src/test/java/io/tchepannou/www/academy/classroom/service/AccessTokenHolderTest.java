package io.tchepannou.www.academy.classroom.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenHolderTest {
    @Mock
    HttpServletRequest request;

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
}
