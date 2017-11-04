package io.tchepannou.www.academy.classroom.exception;

public class SessionException extends RuntimeException {
    public SessionException(final String message) {
        super(message);
    }
    public SessionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
