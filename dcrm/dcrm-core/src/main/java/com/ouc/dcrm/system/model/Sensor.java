package com.ouc.dcrm.system.model;

public class Sensor {
    private Integer sensorid;

    private Integer insid;

    private Integer coordinateid;

    private String sensorname;

    private String unit;

    private Float accuracy;

    private Float upperlimit;

    private Float lowerlimit;

    private Byte type;

    private Byte state;

    private Byte visible;

    public Integer getSensorid() {
        return sensorid;
    }

    public void setSensorid(Integer sensorid) {
        this.sensorid = sensorid;
    }

    public Integer getInsid() {
        return insid;
    }

    public void setInsid(Integer insid) {
        this.insid = insid;
    }

    public Integer getCoordinateid() {
        return coordinateid;
    }

    public void setCoordinateid(Integer coordinateid) {
        this.coordinateid = coordinateid;
    }

    public String getSensorname() {
        return sensorname;
    }

    public void setSensorname(String sensorname) {
        this.sensorname = sensorname == null ? null : sensorname.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Float getUpperlimit() {
        return upperlimit;
    }

    public void setUpperlimit(Float upperlimit) {
        this.upperlimit = upperlimit;
    }

    public Float getLowerlimit() {
        return lowerlimit;
    }

    public void setLowerlimit(Float lowerlimit) {
        this.lowerlimit = lowerlimit;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }
}