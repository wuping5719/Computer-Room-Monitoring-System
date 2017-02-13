package com.ouc.dcrms.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ouc.dcrms.core.service.AlertServiceCore;
import com.ouc.dcrms.core.dao.AlertRecordDAO;
import com.ouc.dcrms.core.model.AlertRecord;

public class AlertServiceCoreImpl implements AlertServiceCore {
    
    private AlertRecordDAO alertRecordDAO;

    @Override
    public String insertAlertRecord(AlertRecord alertRecord) {
	String result = "";
	try {
	    alertRecordDAO.insertSelective(alertRecord);
	    result = "success";
	}catch(Exception e) {
	    result = "failure";
	}
	return result;
    }
    
    @Override
    public AlertRecord getLatestAlert() {
	AlertRecord alertRecord = alertRecordDAO.selectLatestAlert();
	return alertRecord;
    }
    
    @Override
    public int getTotalNum(Integer siteid, Integer reasonlevel,
	    Date startTime, Date endTime) {
	int totalNum = alertRecordDAO.selectTotalNum(siteid, 
		reasonlevel, startTime, endTime);
	return totalNum;
    }
    
    @Override
    public List<AlertRecord> getAlertRecords(Integer siteid, Integer reasonlevel,
	    Date startTime, Date endTime, int startIndex, int pageSize) {
	List<AlertRecord> alertRecordList = new ArrayList<>();
	alertRecordList = alertRecordDAO.selectAlertRecords(siteid, 
		reasonlevel, startTime, endTime, startIndex, pageSize);
	return alertRecordList;
    }
    
    public AlertRecordDAO getAlertRecordDAO() {
	return alertRecordDAO;
    }

    public void setAlertRecordDAO(AlertRecordDAO alertRecordDAO) {
	this.alertRecordDAO = alertRecordDAO;
    }
}
