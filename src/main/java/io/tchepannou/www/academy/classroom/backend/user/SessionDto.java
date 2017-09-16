package io.tchepannou.www.academy.classroom.backend.user;

import java.util.Date;

@SuppressWarnings("CPD-START")
public class SessionDto {
    private Integer id;
    private Integer accountId;
    private Integer roleId;
    private String accessToken;
    private Date expiryDateTime;
    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }
}
