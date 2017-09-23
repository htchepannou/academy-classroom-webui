package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

public class QuizResponse extends BaseResponse {
    private QuizDto quiz;

    public QuizDto getQuiz() {
        return quiz;
    }

    public void setQuiz(final QuizDto quiz) {
        this.quiz = quiz;
    }
}
