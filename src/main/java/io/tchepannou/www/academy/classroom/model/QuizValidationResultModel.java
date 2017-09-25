package io.tchepannou.www.academy.classroom.model;

public class QuizValidationResultModel {
    private boolean valid;
    private String message;

    public boolean isValid() {
        return valid;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
