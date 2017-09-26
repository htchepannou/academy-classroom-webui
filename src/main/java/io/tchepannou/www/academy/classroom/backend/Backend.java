package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.www.academy.classroom.service.Http;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Backend {
    private String url;

    @Autowired
    protected Http http;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
