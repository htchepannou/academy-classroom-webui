package io.tchepannou.www.academy.classroom.model;

@SuppressWarnings("CPD-START")
public class LessonModel extends BaseModel {
    private String title;
    private Integer rank;

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
}
