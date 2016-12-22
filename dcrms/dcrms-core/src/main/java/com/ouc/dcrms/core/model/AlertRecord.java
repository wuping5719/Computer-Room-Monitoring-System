package com.ouc.dcrms.core.model;

import java.util.Date;

public class AlertRecord {
    private Long alertid;

    private Integer siteid;

    private Integer reasonid;

    private Date alerttime;

    private Integer instrumentid;

    private Integer solved;
    
    public Long getAlertid() {
        return alertid;
    }

    public void setAlertid(Long alertid) {
        this.alertid = alertid;
    }

    public Integer getSiteid() {
        return siteid;
    }

    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }

    public Integer getReasonid() {
        return reasonid;
    }

    public void setReasonid(Integer reasonid) {
        this.reasonid = reasonid;
    }

    public Date getAlerttime() {
        return alerttime;
    }

    public void setAlerttime(Date alerttime) {
        this.alerttime = alerttime;
    }

    public Integer getInstrumentid() {
        return instrumentid;
    }

    public void setInstrumentid(Integer instrumentid) {
        this.instrumentid = instrumentid;
    }

    public Integer getSolved() {
	return solved;
    }

    public void setSolved(Integer solved) {
	this.solved = solved;
    }
}