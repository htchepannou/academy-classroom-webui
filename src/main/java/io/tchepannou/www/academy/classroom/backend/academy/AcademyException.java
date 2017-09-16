package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.BackendException;

public class AcademyException extends BackendException{
    public AcademyException(final int statusCode, final String errorDetails, final Throwable cause) {
        super(statusCode, errorDetails, cause);
    }
}
