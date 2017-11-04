package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Backend {
    private String url;

    @Autowired
    protected RestClient restClient;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
