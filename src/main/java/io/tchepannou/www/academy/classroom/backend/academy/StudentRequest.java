package io.tchepannou.www.academy.classroom.backend.academy;

import javax.validation.constraints.NotNull;

public class StudentRequest {
    @NotNull
    private Integer roleId;

    @NotNull
    private Integer segmentId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(final Integer segmentId) {
        this.segmentId = segmentId;
    }
}
