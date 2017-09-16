package io.tchepannou.www.academy.classroom.servlet;

import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.backend.user.UserException;
import io.tchepannou.www.academy.classroom.service.AccessTokenHolder;
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
    private UserBackend userBackend;

    @Autowired
    private AccessTokenHolder accessTokenHolder;

    @Value("${application.login.url}")
    private String loginUrl;

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
        if (shouldLogin(request, response)){
            final String qs = request.getQueryString();

            final StringBuffer targetUrl = request.getRequestURL();
            if (qs != null){
                targetUrl.append('?').append(qs);
            }
            final String doneUrl = URLEncoder.encode(targetUrl.toString(), "utf-8");
            final String url = String.format("%s?done=%s", loginUrl, doneUrl);
            response.sendRedirect(url);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean shouldLogin(final HttpServletRequest request, final HttpServletResponse response){
        String accessToken = accessTokenHolder.get(request);
        if (accessToken == null){
            accessToken = request.getParameter(LoginFilter.PARAM_NAME);
        }

        if (accessToken != null){
            try {
                userBackend.findSessionByToken(accessToken).getSession();
                return false;
            } catch(UserException e){
                if (e.getStatusCode() == 404){
                    LOGGER.info("{} - Access token not found", accessToken, e);
                } else {
                    LOGGER.warn("{} Access token no longer valid", accessToken, e);
                }
            }
        } else {
            LOGGER.info("No access token. User should login");
        }
        return true;

    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(final String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
