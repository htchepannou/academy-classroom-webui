package io.tchepannou.www.academy.classroom.backend.academy;

public class QuizChoiceDto{
    private Integer id;
    private Integer rank;
    private String text;
    private boolean answer;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(final Integer rank) {
        this.rank = rank;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(final boolean answer) {
        this.answer = answer;
    }
}
