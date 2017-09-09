package io.tchepannou.www.academy.classroom.backend.academy.dto;

public class SegmentResponse {
    private SegmentDto segment;

    public SegmentResponse() {
    }

    public SegmentResponse(final SegmentDto segment) {
        this.segment = segment;
    }

    public SegmentDto getSegment() {
        return segment;
    }

    public void setSegment(final SegmentDto segment) {
        this.segment = segment;
    }
}
