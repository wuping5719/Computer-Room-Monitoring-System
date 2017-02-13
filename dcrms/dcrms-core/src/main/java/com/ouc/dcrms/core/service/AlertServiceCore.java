package com.ouc.dcrms.core.service;

import java.util.Date;
import java.util.List;

import com.ouc.dcrms.core.model.AlertRecord;

/**
 * @author WuPing
 */

public interface AlertServiceCore {
    
    public String insertAlertRecord(AlertRecord alertRecord);
    
    public AlertRecord getLatestAlert();
    
    public int getTotalNum(Integer siteid, Integer reasonlevel,
	    Date startTime, Date endTime);
    
    public List<AlertRecord> getAlertRecords(Integer siteid, Integer reasonlevel,
	    Date startTime, Date endTime, int startIndex, int pageSize);
}

