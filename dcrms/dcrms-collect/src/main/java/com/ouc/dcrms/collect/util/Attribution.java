package com.ouc.dcrms.collect.util;

public class Attribution {
    private int globalID; // 设备全局ID
    private String name; // 设备名
    private String englishName; // 设备的英文名
    private String manufacturer; // 设备生产厂家
    private String model; // 设备型号
    private int parameterNum; // 设备参数数目
    private String serialnumber;     // 版本序列号
    private int relativeID; // 设备相对ID
    private int instrumentType; // 设备类型
    private int siteID; // 设备所属站点ID
    
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

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public int getParameterNum() {
	return parameterNum;
    }

    public void setParameterNum(int parameterNum) {
	this.parameterNum = parameterNum;
    }

    public int getRelativeID() {
	return relativeID;
    }

    public void setRelativeID(int relativeID) {
	this.relativeID = relativeID;
    }

    public int getSiteID() {
	return siteID;
    }

    public void setSiteID(int siteID) {
	this.siteID = siteID;
    }

    public String getEnglishName() {
	return englishName;
    }

    public void setEnglishName(String englishName) {
	this.englishName = englishName;
    }

    public String getSerialnumber() {
	return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
	this.serialnumber = serialnumber;
    }

    public int getInstrumentType() {
	return instrumentType;
    }

    public void setInstrumentType(int instrumentType) {
	this.instrumentType = instrumentType;
    }
}
