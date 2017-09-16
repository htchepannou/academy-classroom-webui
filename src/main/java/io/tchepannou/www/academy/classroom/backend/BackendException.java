package io.tchepannou.www.academy.classroom.backend;

public class BackendException extends RuntimeException{
    private int statusCode;
    private String errorDetails;

    public BackendException(int statusCode, String errorDetails, Throwable cause){
        super(cause);

        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }

    @Override
    public String getMessage() {
        return String.format("code=%s - details=%s", statusCode, errorDetails);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
