package io.tchepannou.www.academy.classroom.backend.academy;

import java.util.Date;

@SuppressWarnings("CPD-START")
public class CourseDto {
    private Integer id;
    private String title;
    private String summary;
    private String description;
    private String language;
    private String level;
    private String status;
    private Date publishedDateTime;
    private Date updatedDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getPublishedDateTime() {
        return publishedDateTime;
    }

    public void setPublishedDateTime(final Date publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(final Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
