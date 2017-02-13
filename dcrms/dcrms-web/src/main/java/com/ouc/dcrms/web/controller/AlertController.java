package com.ouc.dcrms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ouc.dcrms.client.dto.CityDTO;
import com.ouc.dcrms.client.service.AlertServiceClient;
import com.ouc.dcrms.client.service.SiteServiceClient;

/*
 * @Author WuPing
 */

@Controller
public class AlertController {

    @Resource(name = "alertServiceClient")
    private AlertServiceClient alertServiceClient;

    @Resource(name = "siteServiceClient")
    private SiteServiceClient siteServiceClient;
    
    @RequestMapping(value = "searchAlertInfos.do", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchUsers(HttpServletRequest request,
	    HttpServletResponse response) {
	String startDate = request.getParameter("startDate");  
	String endDate = request.getParameter("endDate");  
	String siteName = request.getParameter("siteName");
	String reason = request.getParameter("reason");
	String level = request.getParameter("level");
	int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 当前页号

	String result = alertServiceClient.getAlertInfos(startDate, endDate, 
		siteName, reason, level, pageNum);
	
	return result;
    }
    
    @RequestMapping(value = "loadAlertInfos.do", produces = "text/html;charset=UTF-8")
    public String loadAlertInfos(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/alert_infos";
    }
}
