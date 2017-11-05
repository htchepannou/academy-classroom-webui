package io.tchepannou.www.academy.classroom.servlet;

import io.tchepannou.www.academy.classroom.exception.SessionException;
import io.tchepannou.www.academy.classroom.service.SessionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class RequiresUserFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequiresUserFilter.class);

    @Autowired
    private SessionProvider sessionProvider;

    @Value("${application.endpoint.login.url}")
    private String loginAppUrl;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        verify((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void verify(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        if (shouldLogin(request)){
            final String qs = request.getQueryString();

            final StringBuffer targetUrl = request.getRequestURL();
            if (qs != null){
                targetUrl.append('?').append(qs);
            }
            final String doneUrl = URLEncoder.encode(targetUrl.toString(), "utf-8");
            final String url = String.format("%s/login?done=%s", loginAppUrl, doneUrl);

            LOGGER.info("UnAuthorized. Redirecting to {}", url);
            response.sendRedirect(url);
        } else {
            LOGGER.info("Authorized");
            chain.doFilter(request, response);
        }
    }

    private boolean shouldLogin(final HttpServletRequest request){
        try {
            sessionProvider.getCurrentSession(request);
            return false;
        } catch (SessionException e){
            LOGGER.warn("Unable to resolve the session", e);
            return true;
        }
    }

    public String getLoginAppUrl() {
        return loginAppUrl;
    }

    public void setLoginAppUrl(final String loginAppUrl) {
        this.loginAppUrl = loginAppUrl;
    }
}
