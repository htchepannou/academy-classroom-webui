package io.tchepannou.www.academy.classroom.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseModel extends BaseModel {
    private String title;
    private String summary;
    private String description;
    private String language;
    private String level;
    private String status;
    private Date publishedDateTime;
    private Date updatedDateTime;
    private List<LessonModel> lessons = new ArrayList<>();

    public LessonModel getLesson(Integer lessonId){
        return lessons.stream()
                .filter(l -> lessonId.equals(l.getId()))
                .findFirst()
                .orElse(null);
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

    public List<LessonModel> getLessons() {
        return lessons;
    }

    public void setLessons(final List<LessonModel> lessons) {
        this.lessons = lessons;
    }
}
