package com.ouc.dcrms.core.model;

import java.util.Date;

public class SiteData {
    private Long dataid;

    private Date gathertime;

    private String value;

    private Integer siteid;

    public Long getDataid() {
        return dataid;
    }

    public void setDataid(Long dataid) {
        this.dataid = dataid;
    }

    public Date getGathertime() {
        return gathertime;
    }

    public void setGathertime(Date gathertime) {
        this.gathertime = gathertime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getSiteid() {
        return siteid;
    }

    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }
}