package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

public class AttendanceResponse extends BaseResponse {
    private CourseAttendanceDto attendance;

    public CourseAttendanceDto getAttendance() {
        return attendance;
    }

    public void setAttendance(final CourseAttendanceDto attendance) {
        this.attendance = attendance;
    }
}
