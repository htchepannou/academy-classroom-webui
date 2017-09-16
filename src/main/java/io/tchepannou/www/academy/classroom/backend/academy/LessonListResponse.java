package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

import java.util.List;

public class LessonListResponse extends BaseResponse {
    private List<LessonDto> lessons;


    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(final List<LessonDto> lessons) {
        this.lessons = lessons;
    }
}
