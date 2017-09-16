package io.tchepannou.www.academy.classroom.servlet;

import io.tchepannou.www.academy.classroom.backend.user.AuthResponse;
import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.backend.user.UserException;
import io.tchepannou.www.academy.classroom.service.AccessTokenHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequiresUserFilterTest {
    @Mock
    private UserBackend userBackend;

    @Mock
    private AccessTokenHolder accessTokenHolder;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain chain;

    @InjectMocks
    RequiresUserFilter filter;

    @Before
    public void setUp (){
        filter.setLoginUrl("http://login.com");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://test.com"));
    }

    @Test
    public void shouldRedirectToLoginWhenAccessTokenNotAvailable() throws Exception {
        // Given
        when(accessTokenHolder.get(any())).thenReturn(null);

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(response).sendRedirect("http://login.com?done=http%3A%2F%2Ftest.com");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void shouldRedirectToLoginWhenAccessTokenIsInvalid() throws Exception {
        // Given
        when(accessTokenHolder.get(any())).thenReturn("123");
        final UserException ex = new UserException(409, "foo", new Exception());
        doThrow(ex).when(userBackend).findSessionByToken("123");

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(response).sendRedirect("http://login.com?done=http%3A%2F%2Ftest.com");
        verify(chain, never()).doFilter(request, response);
    }


    @Test
    public void shouldAcceptToken() throws Exception {
        // Given
        when(accessTokenHolder.get(any())).thenReturn("123");
        final AuthResponse resp = mock(AuthResponse.class);
        when(userBackend.findSessionByToken("123")).thenReturn(resp);

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }
}
