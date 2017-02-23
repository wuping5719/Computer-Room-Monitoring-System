package com.ouc.dcrms.core.model;

import java.io.Serializable;
import java.util.Date;

public class AlertRecord implements Serializable {

    private static final long serialVersionUID = 8933925573746363700L;

    private Long alertid;

    private Integer siteid;

    private Integer reasonid;
    
    private Integer reasonlevel;

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

    public Integer getReasonlevel() {
	return reasonlevel;
    }

    public void setReasonlevel(Integer reasonlevel) {
	this.reasonlevel = reasonlevel;
    }
}