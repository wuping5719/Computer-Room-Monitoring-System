package com.ouc.dcrms.core.model;

import java.io.Serializable;
import java.util.Date;

public class Logs implements Serializable {

    private static final long serialVersionUID = 225303465410282142L;

    private Integer logid;

    private Integer userid;

    private Integer actionid;

    private Date recordtime;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }
}