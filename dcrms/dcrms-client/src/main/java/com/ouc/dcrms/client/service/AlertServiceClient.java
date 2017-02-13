package com.ouc.dcrms.client.service;

/**
 * @author WuPing
 */

public interface AlertServiceClient {

    public String getAlertInfos(String startDate, String endDate, 
	   String siteName, String reason, String level, int pageNum);
    
}

