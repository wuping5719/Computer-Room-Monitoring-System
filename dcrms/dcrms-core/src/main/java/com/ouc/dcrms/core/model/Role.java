package com.ouc.dcrms.core.model;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
  
    private static final long serialVersionUID = 6247739169332243696L;

    private Integer roleid;

    private String rolename;

    private String description;

    private String usersequence;

    private String ressequence;
    
    private Date gmtCreate;
    
    private Date gmtModified;
    
    private String createBy;
    
    private String lastModifedBy;

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

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
	return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
	this.gmtModified = gmtModified;
    }

    public String getCreateBy() {
	return createBy;
    }

    public void setCreateBy(String createBy) {
	this.createBy = createBy;
    }

    public String getLastModifedBy() {
	return lastModifedBy;
    }

    public void setLastModifedBy(String lastModifedBy) {
	this.lastModifedBy = lastModifedBy;
    }
}