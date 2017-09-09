
package io.tchepannou.www.academy.classroom.model;

@SuppressWarnings("CPD-START")
public class VideoModel {
    private Integer id;
    private String type;
    private Integer durationSecond;
    private String embedUrl;
    private String videoId;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getDurationSecond() {
        return durationSecond;
    }

    public void setDurationSecond(final Integer durationSecond) {
        this.durationSecond = durationSecond;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(final String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(final String videoId) {
        this.videoId = videoId;
    }
}
