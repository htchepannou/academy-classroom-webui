package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.academy.client.video.VideoResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.backend.VideoBackend")
public class VideoBackend extends Backend{
    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/%s", getUrl(), videoId);
        return restClient.get(uri, VideoResponse.class).getBody();
    }
}
