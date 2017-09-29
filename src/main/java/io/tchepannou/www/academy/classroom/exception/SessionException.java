package io.tchepannou.www.academy.classroom.exception;

public class SessionException extends RuntimeException {
    private int statusCode;
    private String details;

    public SessionException(final int statusCode, final String details) {
        this.statusCode = statusCode;
        this.details = details;
    }

    public SessionException(final int statusCode, final String details, final Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
        this.details = details;
    }

    @Override
    public String getMessage() {
        return String.format("%s - %s", this.statusCode, this.details);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDetails() {
        return details;
    }
}
