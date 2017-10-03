package io.tchepannou.www.academy.classroom.model;

import java.util.List;

@SuppressWarnings("CPD-START")
public class LessonModel extends BaseModel {
    private String title;
    private Integer rank;
    private List<SegmentModel> segments;

    public SegmentModel getSegment(Integer id){
        return segments.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(final Integer rank) {
        this.rank = rank;
    }

    public List<SegmentModel> getSegments() {
        return segments;
    }

    public void setSegments(final List<SegmentModel> segments) {
        this.segments = segments;
    }
}
