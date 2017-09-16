package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

import java.util.List;

public class SegmentListResponse extends BaseResponse {
    private List<SegmentDto> segments;

    public List<SegmentDto> getSegments() {
        return segments;
    }

    public void setSegments(final List<SegmentDto> segments) {
        this.segments = segments;
    }
}
