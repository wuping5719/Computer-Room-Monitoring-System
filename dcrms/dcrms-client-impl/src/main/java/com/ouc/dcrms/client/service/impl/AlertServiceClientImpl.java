package com.ouc.dcrms.client.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ouc.dcrms.client.service.AlertServiceClient;
import com.ouc.dcrms.core.service.AlertServiceCore;
import com.ouc.dcrms.core.service.InstrumentServiceCore;
import com.ouc.dcrms.core.service.SiteServiceCore;
import com.ouc.dcrms.core.service.CityServiceCore;
import com.ouc.dcrms.core.service.ReasonServiceCore;
import com.ouc.dcrms.core.model.AlertRecord;
import com.ouc.dcrms.core.model.Reason;
import com.ouc.dcrms.core.model.Site;

public class AlertServiceClientImpl implements AlertServiceClient {
    
    private AlertServiceCore alertServiceCore;
    
    private SiteServiceCore siteServiceCore;
    
    private InstrumentServiceCore instrumentServiceCore;
    
    private CityServiceCore cityServiceCore;
    
    private ReasonServiceCore reasonServiceCore;

    @Override
    public String getAlertInfos(String startDate, String endDate, 
	          String siteName, String reason, String level, int pageNum) {
	int totalNum = 0;   // 总记录数
	int totalPage = 1;  // 总页数
	int pageSize = 20;
	int startIndex = 0; // 开始索引
	
	int siteid = -1;
	
	int reasonlevel = -1;
	if(level != null) {
	    if(level.equals("紧急报警")) {
		reasonlevel = 0;
	    } else if(level.equals("重要报警")) {
		reasonlevel = 1;
	    } else if(level.equals("一般报警")) {
		reasonlevel = 2;
	    }
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date startTime = null;
	Date endTime = null;
	if((!startDate.equals("") && startDate != null) 
	      && (!endDate.equals("") && endDate != null)) {
	    try {
		startTime = sdf.parse(startDate);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    
	    try {
		endTime = sdf.parse(endDate);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	}
	
	totalNum = alertServiceCore.getTotalNum(siteid, reasonlevel, startTime, endTime);
	
	totalPage = totalNum % pageSize == 0 
		? totalNum / pageSize
		: totalNum / pageSize + 1;
	
	if (totalNum > 0) {
	    if (pageNum <= 1) {
		startIndex = 0;
	    } else {
		startIndex = pageNum * pageSize - pageSize;
	    }
	}
	
	List<AlertRecord> alertRecordList = new ArrayList<>();
	alertRecordList = alertServiceCore.getAlertRecords(siteid, 
		reasonlevel, startTime, endTime, startIndex, pageSize);

	int n = startIndex + 1;   // 设置序号
	JSONArray jsonArray = new JSONArray();
	for(AlertRecord alertRecord: alertRecordList) {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sortIndex", n);
	    
	    Site tempSite = siteServiceCore.selectByPrimaryKey(alertRecord.getSiteid());
	    jsonObject.put("siteName", tempSite.getSitename());
	    
	    String tempInsName = instrumentServiceCore.selectByPrimaryKey(alertRecord.getInstrumentid()).getInsname();
	    jsonObject.put("insName", tempInsName);
	    
	    jsonObject.put("alertTime", sdf.format(alertRecord.getAlerttime()));
	    jsonObject.put("reasonLevel", alertRecord.getReasonlevel());
	    if (alertRecord.getReasonlevel() == 0) {
		jsonObject.put("reasonLevel", "紧急报警");
	    } else if (alertRecord.getReasonlevel() == 1) {
		jsonObject.put("reasonLevel", "重要报警");
	    } else {
		jsonObject.put("reasonLevel", "一般报警");
	    }
	    
	    if (alertRecord.getSolved() == 0) {
		jsonObject.put("isSolved", "取消报警");
	    } else {
		jsonObject.put("isSolved", "已处理");
	    }
	    jsonObject.put("id", alertRecord.getAlertid());
	    
	    Reason tempReason = reasonServiceCore.selectByPrimaryKey(alertRecord.getReasonid());
	    if (tempReason.getType() == 0) {
		jsonObject.put("reasonType", "开关量");
	    } else if (tempReason.getType() == 1) {
		jsonObject.put("reasonType", "模拟量");
	    } else {
		jsonObject.put("reasonType", "开关量+模拟量");
	    }
	    jsonObject.put("description", tempReason.getDescription());
	    
	    String tempCityName = cityServiceCore.selectByPrimaryKey(tempSite.getCityid()).getCityname();
	    jsonObject.put("cityName", tempCityName);
	    
	    jsonArray.add(jsonObject);
	    n++;
	}

	Map<String, Object> result = new HashMap<String, Object>(3);
	result.put("alertInfoDTOs", jsonArray);
	result.put("alertsTotalNum", totalNum);
	result.put("alertsTotalPage", totalPage);
	return JSONObject.fromObject(result).toString();
    }
    
    public AlertServiceCore getUserServiceCore() {
	return alertServiceCore;
    }

    public void setAlertServiceCore(AlertServiceCore alertServiceCore) {
	this.alertServiceCore = alertServiceCore;
    }

    public SiteServiceCore getSiteServiceCore() {
	return siteServiceCore;
    }

    public void setSiteServiceCore(SiteServiceCore siteServiceCore) {
	this.siteServiceCore = siteServiceCore;
    }

    public InstrumentServiceCore getInstrumentServiceCore() {
	return instrumentServiceCore;
    }

    public void setInstrumentServiceCore(InstrumentServiceCore instrumentServiceCore) {
	this.instrumentServiceCore = instrumentServiceCore;
    }

    public CityServiceCore getCityServiceCore() {
	return cityServiceCore;
    }

    public void setCityServiceCore(CityServiceCore cityServiceCore) {
	this.cityServiceCore = cityServiceCore;
    }

    public ReasonServiceCore getReasonServiceCore() {
	return reasonServiceCore;
    }

    public void setReasonServiceCore(ReasonServiceCore reasonServiceCore) {
	this.reasonServiceCore = reasonServiceCore;
    }
    
}
