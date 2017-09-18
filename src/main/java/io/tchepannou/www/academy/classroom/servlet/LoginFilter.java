package io.tchepannou.www.academy.classroom.servlet;

import io.tchepannou.www.academy.classroom.service.SessionProvider;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);
    public static final String PARAM_NAME = "guid";

    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        login((HttpServletRequest)request, (HttpServletResponse)response, chain);
        chain.doFilter(request, response);
    }

    private void login(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        String accessToken = request.getParameter(PARAM_NAME);
        if (!StringUtil.isBlank(accessToken)){
            LOGGER.info("Authenticated");
            sessionProvider.setAccessToken(accessToken, response);
        }
    }
}
