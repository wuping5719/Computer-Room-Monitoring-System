package com.ouc.dcrm.system.model;

public class Role {
    private Integer roleid;

    private String rolename;

    private String description;

    private String usersequence;

    private String ressequence;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUsersequence() {
        return usersequence;
    }

    public void setUsersequence(String usersequence) {
        this.usersequence = usersequence == null ? null : usersequence.trim();
    }

    public String getRessequence() {
        return ressequence;
    }

    public void setRessequence(String ressequence) {
        this.ressequence = ressequence == null ? null : ressequence.trim();
    }
}