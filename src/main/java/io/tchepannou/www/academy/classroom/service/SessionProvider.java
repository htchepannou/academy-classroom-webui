package io.tchepannou.www.academy.classroom.service;

import io.tchepannou.www.academy.classroom.backend.user.SessionDto;
import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.backend.user.UserException;
import io.tchepannou.www.academy.classroom.exception.SessionException;
import io.tchepannou.www.academy.classroom.model.SessionModel;
import io.tchepannou.www.academy.classroom.servlet.LoginFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class SessionProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionProvider.class);
    public static final String COOKIE_GUID = "guid";
    public static final String ATTR_SESSION = "io.tchepannou.session";

    @Autowired
    private UserBackend userBackend;

    @Autowired
    private AcademyMapper mapper;

    private int maxAgeSeconds = 24*60*60*365;   // 1 year

    public SessionModel getCurrentSession(final HttpServletRequest request) throws SessionException{
        // Get cached session
        SessionModel session = (SessionModel)request.getAttribute(ATTR_SESSION);
        if (session != null){
            return session;
        }

        // Resolve session from access token
        final String accessToken = getAccessToken(request);
        if (accessToken == null) {
            throw new SessionException(404, "No access token");
        }

        try {
            final SessionDto dto = userBackend.findSessionByToken(accessToken).getSession();
            session = mapper.toSessionModel(dto);
            request.setAttribute(ATTR_SESSION, session);
            return session;
        } catch (UserException e){
            throw new SessionException(e.getStatusCode(), e.getErrorDetails(), e);
        }
    }

    public void setAccessToken(final String accessToken, final HttpServletRequest request, final HttpServletResponse response){
        final Cookie old = getCookie(request);
        final int version = old == null ? 1 : old.getVersion()+1;

        final Cookie cookie = new Cookie(COOKIE_GUID, accessToken);
        cookie.setPath("/");
        cookie.setMaxAge(this.maxAgeSeconds);
        cookie.setVersion(version);
        response.addCookie(cookie);
    }

    public String getAccessToken(final HttpServletRequest request){
        String accessToken = request.getParameter(LoginFilter.PARAM_NAME);
        if (!StringUtils.isEmpty(accessToken)){
            LOGGER.info("AccessToken found from request parameter: {}", accessToken);
        } else {
            final Cookie cookie = getCookie(request);
            if (cookie != null) {
                accessToken = cookie.getValue();
                LOGGER.info("AccessToken found from cookies: {}", accessToken);
            } else {
                LOGGER.info("AccessToken not found");
            }
        }

        return accessToken;
    }

    private Cookie getCookie(final HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        final List<Cookie> found = new ArrayList();
        for (Cookie cookie : cookies){
            if (COOKIE_GUID.equals(cookie.getName())){
                LOGGER.info("cookie: name={}, value={}, maxAge={}, version={}",
                        cookie.getName(),
                        cookie.getValue(),
                        cookie.getMaxAge(),
                        cookie.getVersion()
                );
                found.add(cookie);
            }
        }

        if (found.size() > 0){
            LOGGER.info("Found {} cookies", found.size());
            return found.get(found.size()-1);
        }

        return null;
    }

    public int getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(final int maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }
}
