package com.ouc.dcrms.core.model;

import java.io.Serializable;

public class Actiontype implements Serializable {

    private static final long serialVersionUID = -3987805807556339367L;

    private Integer actionid;

    private String name;

    private Byte type;

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}