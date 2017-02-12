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
public class VideoController {

    @Resource(name = "siteServiceClient")
    private SiteServiceClient siteServiceClient;

    @RequestMapping(value = "loadMultVideo.do", produces = "text/html;charset=UTF-8")
    public String loadIndex(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/mult_video";
    }
    
}
