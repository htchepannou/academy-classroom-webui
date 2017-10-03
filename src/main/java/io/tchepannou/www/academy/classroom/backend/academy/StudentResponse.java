package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

public class StudentResponse extends BaseResponse {
    private StudentDto student;

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(final StudentDto student) {
        this.student = student;
    }
}
