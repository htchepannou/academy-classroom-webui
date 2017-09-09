package io.tchepannou.www.academy.classroom.backend.academy.dto;

import java.util.Date;

public class BaseResponse {
    private String transactionId;
    private Date transactionDateTime;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(final Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
}

