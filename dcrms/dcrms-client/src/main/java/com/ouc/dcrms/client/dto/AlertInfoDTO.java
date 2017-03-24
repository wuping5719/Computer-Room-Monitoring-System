package com.ouc.dcrms.client.dto;

import java.io.Serializable;

public class AlertInfoDTO implements Serializable {

	private static final long serialVersionUID = 2320911252995100502L;

	private Long sortIndex;

	private String siteName;

	private String insName;

	private String alertTime;

	private String reasonLevel;

	private String reasonType;

	private String description;

	private String isSolved;

	private String cityName;

	private Long alertId;

	public Long getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Long sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}

	public String getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}

	public String getReasonLevel() {
		return reasonLevel;
	}

	public void setReasonLevel(String reasonLevel) {
		this.reasonLevel = reasonLevel;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getIsSolved() {
		return isSolved;
	}

	public void setIsSolved(String isSolved) {
		this.isSolved = isSolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}
}
