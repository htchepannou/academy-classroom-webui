package io.tchepannou.www.academy.classroom.backend.academy.dto;

public class CourseResponse extends BaseResponse {
    private CourseDto course;

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(final CourseDto course) {
        this.course = course;
    }
}
