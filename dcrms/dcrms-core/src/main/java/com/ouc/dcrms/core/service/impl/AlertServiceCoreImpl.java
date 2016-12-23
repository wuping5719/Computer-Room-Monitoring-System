package com.ouc.dcrms.core.service.impl;

import com.ouc.dcrms.core.service.AlertServiceCore;
import com.ouc.dcrms.core.dao.AlertRecordDao;
import com.ouc.dcrms.core.model.AlertRecord;

public class AlertServiceCoreImpl implements AlertServiceCore {
    
    private AlertRecordDao alertRecordDao;

    public AlertRecordDao getAlertRecordDao() {
	return alertRecordDao;
    }

    public void setAlertRecordDao(AlertRecordDao alertRecordDao) {
	this.alertRecordDao = alertRecordDao;
    }

    @Override
    public String insertAlertRecord(AlertRecord alertRecord) {
	String result = "";
	try {
	    alertRecordDao.insertSelective(alertRecord);
	    result = "success";
	}catch(Exception e) {
	    result = "failure";
	}
	return result;
    }
}
