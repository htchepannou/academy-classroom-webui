package io.tchepannou.www.academy.classroom.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessTokenHolder {
    private static final String COOKIE_NAME = "guid";

    public String get(final HttpServletRequest request){
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

    public void set(final String accessToken, final HttpServletResponse response){
        response.addCookie(new Cookie(COOKIE_NAME, accessToken));
    }
}
