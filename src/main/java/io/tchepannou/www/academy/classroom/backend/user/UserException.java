package io.tchepannou.www.academy.classroom.backend.user;

import io.tchepannou.www.academy.classroom.backend.BackendException;

public class UserException extends BackendException{
    public UserException(final int statusCode, final String errorDetails, final Throwable cause) {
        super(statusCode, errorDetails, cause);
    }
}
