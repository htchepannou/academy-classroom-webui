package io.tchepannou.www.academy.classroom.model;

import java.util.List;

@SuppressWarnings("CPD-START")
public class QuizModel {
    private Integer id;
    private String type;
    private String question;
    private String description;
    private String answer;
    private VideoModel video;
    private List<QuizChoiceModel> choices;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public VideoModel getVideo() {
        return video;
    }

    public void setVideo(final VideoModel video) {
        this.video = video;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public List<QuizChoiceModel> getChoices() {
        return choices;
    }

    public void setChoices(final List<QuizChoiceModel> choices) {
        this.choices = choices;
    }
}
