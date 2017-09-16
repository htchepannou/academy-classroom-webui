package io.tchepannou.www.academy.classroom.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public abstract class Backend {
    private String url;

    @Autowired
    protected RestTemplate rest;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
