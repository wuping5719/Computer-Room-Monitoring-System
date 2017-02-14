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
import com.ouc.dcrms.client.service.AuthorityServiceClient;
import com.ouc.dcrms.client.service.SiteServiceClient;

/*
 * @Author WuPing
 */

@Controller
public class AuthorityController {

    @Resource(name = "authorityServiceClient")
    private AuthorityServiceClient authorityServiceClient;

    @Resource(name = "siteServiceClient")
    private SiteServiceClient siteServiceClient;
    
    @RequestMapping(value = "loadRoleList.do", produces = "text/html;charset=UTF-8")
    public String loadRoleList(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/role_list";
    }
    
    @RequestMapping(value = "searchRoles.do", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchRoles(HttpServletRequest request,
	    HttpServletResponse response) {
	String roleName = request.getParameter("roleName");  
	String description = request.getParameter("description");  
	int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 当前页号

	String result = authorityServiceClient.getRoleList(roleName, description, pageNum);

	return result;
    }
}
