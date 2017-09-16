package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

public class CourseResponse extends BaseResponse {
    private CourseDto course;

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(final CourseDto course) {
        this.course = course;
    }
}
