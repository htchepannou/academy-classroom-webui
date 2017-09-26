package io.tchepannou.www.academy.classroom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Http {
    private static final Logger LOGGER = LoggerFactory.getLogger(Http.class);
    @Autowired
    protected RestTemplate rest;

    public <T> T get (final String url, Class<T> responseType){
        LOGGER.info("GET {}", url);

        final ResponseEntity<T> response = rest.getForEntity(url, responseType);
        LOGGER.info("{}", response.getStatusCode());
        return response.getBody();
    }

    public void post(final String url){
        LOGGER.info("POST {}", url);

        rest.postForLocation(url, null);
    }
    public <T> T post(final String url, Object request, Class<T> responseType){
        LOGGER.info("POST {}", url);

        final ResponseEntity<T> response = rest.postForEntity(url, request, responseType);
        LOGGER.info("{}", response.getStatusCode());
        return response.getBody();
    }
}
