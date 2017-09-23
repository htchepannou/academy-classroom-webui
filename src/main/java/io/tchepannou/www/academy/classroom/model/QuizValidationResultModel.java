package io.tchepannou.www.academy.classroom.model;

public class QuizValidationResultModel {
    private boolean valid;
    private String nextUrl;

    public boolean isValid() {
        return valid;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(final String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
