package io.tchepannou.www.academy.classroom.servlet;

import io.tchepannou.www.academy.classroom.exception.SessionException;
import io.tchepannou.www.academy.classroom.model.SessionModel;
import io.tchepannou.www.academy.classroom.service.SessionProvider;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequiresUserFilterTest {
    @Mock
    private SessionProvider sessionProvider;

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
    public void shouldUnauthorizeUserWhenNoCurrentSession() throws Exception {
        // Given
        when(sessionProvider.getCurrentSession(any())).thenThrow(new SessionException(1, ""));

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(response).sendRedirect("http://login.com?done=http%3A%2F%2Ftest.com");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void shouldAuthorizeUserWhenCurrentSession() throws Exception {
        // Given
        final SessionModel session = new SessionModel();
        when(sessionProvider.getCurrentSession(any())).thenReturn(session);

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }
}
