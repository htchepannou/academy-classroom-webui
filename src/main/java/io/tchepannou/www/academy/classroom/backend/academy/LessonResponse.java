package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

public class LessonResponse extends BaseResponse {
    private LessonDto lesson;

    public LessonDto getLesson() {
        return lesson;
    }

    public void setLesson(final LessonDto lesson) {
        this.lesson = lesson;
    }
}
