package io.tchepannou.www.academy.classroom.servlet;

import io.tchepannou.www.academy.classroom.service.AccessTokenHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginFilterTest {
    @Mock
    private AccessTokenHolder accessTokenHolder;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain chain;

    @InjectMocks
    LoginFilter filter;

    @Test
    public void doShouldSetAccessTokenWhenGuidParameterPresent() throws Exception {
        // Given
        when(request.getParameter("guid")).thenReturn("123");

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(accessTokenHolder).set("123", response);
        verify(chain).doFilter(request, response);

    }


    @Test
    public void doNotShouldSetAccessTokenWhenGuidParameterNotPresent() throws Exception {
        // Given
        when(request.getParameter("guid")).thenReturn(null);

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(accessTokenHolder, never()).set(anyString(), any(HttpServletResponse.class));
        verify(chain).doFilter(request, response);

    }
}