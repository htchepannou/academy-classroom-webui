package io.tchepannou.www.academy.classroom.backend.academy;

import java.util.Date;

@SuppressWarnings("CPD-START")
public class SegmentAttendanceDto {
    private Integer id;
    private Integer segmentId;
    private Date attendanceDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(final Integer segmentId) {
        this.segmentId = segmentId;
    }

    public Date getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public void setAttendanceDateTime(final Date attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }
}
