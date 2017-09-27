package io.tchepannou.www.academy.classroom.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggerFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        log((HttpServletRequest)servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void log(final HttpServletRequest request) throws IOException, ServletException {
        final String qs = request.getQueryString();
        LOGGER.info("{} {}{}", request.getMethod(), request.getRequestURL(), qs == null ? "" : "?" + qs);
    }

    @Override
    public void destroy() {

    }
}
