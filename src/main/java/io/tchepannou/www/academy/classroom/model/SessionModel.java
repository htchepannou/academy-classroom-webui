package io.tchepannou.www.academy.classroom.model;

import java.util.Date;

public class SessionModel extends BaseModel{
    private Integer accountId;
    private Integer roleId;
    private String accessToken;
    private Date expiryDateTime;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(final Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(final Date expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }
}
