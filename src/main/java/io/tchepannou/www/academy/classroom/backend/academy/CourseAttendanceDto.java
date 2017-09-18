package io.tchepannou.www.academy.classroom.backend.academy;

import java.util.Date;
import java.util.List;

@SuppressWarnings("CPD-START")
public class CourseAttendanceDto {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Integer currentSegmentId;
    private Date attendanceDateTime;
    private List<SegmentAttendanceDto> segments;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(final Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(final Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCurrentSegmentId() {
        return currentSegmentId;
    }

    public void setCurrentSegmentId(final Integer currentSegmentId) {
        this.currentSegmentId = currentSegmentId;
    }

    public Date getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public void setAttendanceDateTime(final Date attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public List<SegmentAttendanceDto> getSegments() {
        return segments;
    }

    public void setSegments(final List<SegmentAttendanceDto> segments) {
        this.segments = segments;
    }
}
