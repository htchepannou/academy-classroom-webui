package io.tchepannou.www.academy.classroom.backend.academy.dto;

public class LessonResponse extends BaseResponse{
    private LessonDto lesson;

    public LessonDto getLesson() {
        return lesson;
    }

    public void setLesson(final LessonDto lesson) {
        this.lesson = lesson;
    }
}
