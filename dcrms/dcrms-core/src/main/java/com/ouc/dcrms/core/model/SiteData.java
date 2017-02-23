package com.ouc.dcrms.core.model;

import java.io.Serializable;
import java.util.Date;

public class SiteData implements Serializable {

    private static final long serialVersionUID = -1933743000271371263L;

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