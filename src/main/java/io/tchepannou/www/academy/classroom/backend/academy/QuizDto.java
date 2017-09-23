package io.tchepannou.www.academy.classroom.backend.academy;

import java.util.List;

@SuppressWarnings("CPD-START")
public class QuizDto {
    private Integer id;
    private String type;
    private Integer videoId;
    private String question;
    private String description;
    private String answer;
    private List<QuizChoiceDto> choices;

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

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(final Integer videoId) {
        this.videoId = videoId;
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

    public List<QuizChoiceDto> getChoices() {
        return choices;
    }

    public void setChoices(final List<QuizChoiceDto> choices) {
        this.choices = choices;
    }
}
