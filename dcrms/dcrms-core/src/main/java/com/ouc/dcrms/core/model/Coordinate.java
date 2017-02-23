package com.ouc.dcrms.core.model;

import java.io.Serializable;

public class Coordinate implements Serializable {
    
    private static final long serialVersionUID = 2385122626114187416L;

    private Integer coordinateid;

    private String coordinatename;

    private String unit;

    private Float uplimit;

    private Float lowlimit;

    private Byte visible;

    public Integer getCoordinateid() {
        return coordinateid;
    }

    public void setCoordinateid(Integer coordinateid) {
        this.coordinateid = coordinateid;
    }

    public String getCoordinatename() {
        return coordinatename;
    }

    public void setCoordinatename(String coordinatename) {
        this.coordinatename = coordinatename == null ? null : coordinatename.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Float getUplimit() {
        return uplimit;
    }

    public void setUplimit(Float uplimit) {
        this.uplimit = uplimit;
    }

    public Float getLowlimit() {
        return lowlimit;
    }

    public void setLowlimit(Float lowlimit) {
        this.lowlimit = lowlimit;
    }

    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }
}