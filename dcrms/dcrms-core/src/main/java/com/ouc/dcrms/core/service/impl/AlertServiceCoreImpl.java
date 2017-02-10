package com.ouc.dcrms.core.service.impl;

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
    
    public AlertRecordDAO getAlertRecordDAO() {
	return alertRecordDAO;
    }

    public void setAlertRecordDAO(AlertRecordDAO alertRecordDAO) {
	this.alertRecordDAO = alertRecordDAO;
    }
}
