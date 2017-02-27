package com.ouc.dcrms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ouc.dcrms.client.dto.CityDTO;
import com.ouc.dcrms.client.service.SiteServiceClient;

/*
 * @Author WuPing
 */

@Controller
public class DeviceController {

    @Resource(name = "siteServiceClient")
    private SiteServiceClient siteServiceClient;

    @RequestMapping(value = "loadDeviceManage.do", produces = "text/html;charset=UTF-8")
    public String loadDeviceManage(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/device_manage_conditioner";
    }
    
    @RequestMapping(value = "loadDeviceManageUPS.do", produces = "text/html;charset=UTF-8")
    public String loadDeviceManageUPS(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/device_manage_ups";
    }
    
    @RequestMapping(value = "loadDeviceAcquisitionConfig.do", produces = "text/html;charset=UTF-8")
    public String loadDeviceAcquisitionConfig(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/device_acquisition_config";
    }
    
    @RequestMapping(value = "loadDeviceManageCabinet.do", produces = "text/html;charset=UTF-8")
    public String loadDeviceManageCabinet(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/device_manage_cabinet";
    }
}
