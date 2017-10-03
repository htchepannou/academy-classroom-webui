package io.tchepannou.www.academy.classroom.backend.academy;

import java.util.List;

@SuppressWarnings("CPD-START")
public class StudentDto {
    private Integer id;
    private Integer roleId;
    private Integer courseId;
    private Integer courseSegmentCount;
    private Integer attendedSegmentCount;
    private List<StudentSegmentDto> segments;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(final Integer courseId) {
        this.courseId = courseId;
    }

    public List<StudentSegmentDto> getSegments() {
        return segments;
    }

    public void setSegments(final List<StudentSegmentDto> segments) {
        this.segments = segments;
    }

    public Integer getCourseSegmentCount() {
        return courseSegmentCount;
    }

    public void setCourseSegmentCount(final Integer courseSegmentCount) {
        this.courseSegmentCount = courseSegmentCount;
    }

    public Integer getAttendedSegmentCount() {
        return attendedSegmentCount;
    }

    public void setAttendedSegmentCount(final Integer attendedSegmentCount) {
        this.attendedSegmentCount = attendedSegmentCount;
    }
}
