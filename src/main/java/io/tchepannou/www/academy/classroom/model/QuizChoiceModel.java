package io.tchepannou.www.academy.classroom.model;

@SuppressWarnings("CPD-START")
public class QuizChoiceModel {
    private Integer id;
    private String text;
    private boolean answer;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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
