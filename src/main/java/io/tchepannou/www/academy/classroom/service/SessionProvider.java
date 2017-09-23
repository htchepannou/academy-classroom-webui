package io.tchepannou.www.academy.classroom.service;

import io.tchepannou.www.academy.classroom.backend.user.SessionDto;
import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.backend.user.UserException;
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
import java.util.Optional;

@Component
public class SessionProvider {
    private static final String COOKIE_NAME = "guid";
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionProvider.class);

    @Autowired
    private UserBackend userBackend;

    @Autowired
    private AcademyMapper mapper;

    public Optional<SessionModel> getCurrentSession(final HttpServletRequest request){
        final String accessToken = getAccessToken(request);
        if (accessToken != null){
            try {
                final SessionDto dto = userBackend.findSessionByToken(accessToken).getSession();
                final SessionModel session = mapper.toSessionModel(dto);
                return Optional.of(session);
            } catch (UserException e){
                if (e.getStatusCode() == 404){
                    LOGGER.info("{} - Access token not found", accessToken, e);
                } else {
                    LOGGER.warn("{} Access token no longer valid", accessToken, e);
                }
            }
        }
        return Optional.empty();
    }

    public void setAccessToken(final String accessToken, final HttpServletResponse response){
        response.addCookie(new Cookie(COOKIE_NAME, accessToken));
    }

    public String getAccessToken(final HttpServletRequest request){
        final String accessToken = request.getParameter(LoginFilter.PARAM_NAME);
        if (!StringUtils.isEmpty(accessToken)){
            return accessToken;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie : cookies){
            if (COOKIE_NAME.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}