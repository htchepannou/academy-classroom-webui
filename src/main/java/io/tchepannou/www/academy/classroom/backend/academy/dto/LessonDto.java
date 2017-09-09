package io.tchepannou.www.academy.classroom.backend.academy.dto;

@SuppressWarnings("CPD-START")
public class LessonDto {
    private Integer id;
    private String title;
    private Integer rank;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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
}
