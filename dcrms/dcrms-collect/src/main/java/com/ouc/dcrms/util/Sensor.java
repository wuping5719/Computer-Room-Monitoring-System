package com.ouc.dcrms.util;

public class Sensor {
    private int globalID; // 传感器全局ID
    private String name; // 传感器名称
    private String unit; // 传感器单位
    private int internalIdentifier; // 内部标识
    private int relativeID; // 传感器相对ID

    public int getGlobalID() {
	return globalID;
    }

    public void setGlobalID(int globalID) {
	this.globalID = globalID;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public int getInternalIdentifier() {
	return internalIdentifier;
    }

    public void setInternalIdentifier(int internalIdentifier) {
	this.internalIdentifier = internalIdentifier;
    }

    public int getRelativeID() {
	return relativeID;
    }

    public void setRelativeID(int relativeID) {
	this.relativeID = relativeID;
    }
}
