package com.ouc.dcrms.core.model;

import java.io.Serializable;

public class Reason implements Serializable {

    private static final long serialVersionUID = 5455328018985540237L;

    private Integer reasonid;

    private String description;

    private Byte level;

    private Byte type;

    public Integer getReasonid() {
        return reasonid;
    }

    public void setReasonid(Integer reasonid) {
        this.reasonid = reasonid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}