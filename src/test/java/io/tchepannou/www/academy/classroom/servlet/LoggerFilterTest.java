package io.tchepannou.www.academy.classroom.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggerFilterTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain chain;

    @Mock
    Logger logger;

    LoggerFilter filter;


    @Before
    public void setUp (){
        filter = new LoggerFilter(logger);

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://www.google.ca"));
    }

    @Test
    public void shouldLogRequest() throws Exception {
        // Given

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(logger).info("GET http://www.google.ca");
        verify(chain).doFilter(request, response);
    }


    @Test
    public void shouldLogRequestQueryString() throws Exception {
        // Given
        when(request.getQueryString()).thenReturn("s1=1&s2=2");

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(logger).info("GET http://www.google.ca?s1=1&s2=2");
        verify(chain).doFilter(request, response);

    }
}
