package io.tchepannou.www.academy.classroom.backend.academy.dto;

public class VideoResponse extends BaseResponse {
    private VideoDto video;

    public VideoResponse() {
    }

    public VideoResponse(final VideoDto video) {
        this.video = video;
    }

    public VideoDto getVideo() {
        return video;
    }

    public void setVideo(final VideoDto video) {
        this.video = video;
    }
}
