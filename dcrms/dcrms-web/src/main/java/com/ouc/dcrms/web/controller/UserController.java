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
import com.ouc.dcrms.client.dto.UserDTO;
import com.ouc.dcrms.client.service.SiteServiceClient;
import com.ouc.dcrms.client.service.UserServiceClient;

/*
 * @Author WuPing
 */

@Controller
public class UserController {

    @Resource(name = "userServiceClient")
    private UserServiceClient userServiceClient;

    @Resource(name = "siteServiceClient")
    private SiteServiceClient siteServiceClient;
    
    // 用户注册
    @RequestMapping(value = "userRegister.do", produces = "text/html;charset=UTF-8")
    public void userRegister(HttpServletRequest request,
	    HttpServletResponse response) {
	String username = request.getParameter("username");  // 用户名(登录名)
	String password = request.getParameter("password");  // 密码
	String trueName = request.getParameter("trueName");  // 姓名
	String telephone = request.getParameter("telephone");  // 手机
	String email = request.getParameter("email");      // 邮箱
	String sex = request.getParameter("sex");     // 性别

	UserDTO userDTO = new UserDTO();
	userDTO.setUsername(username);
	userDTO.setPassword(password);
	userDTO.setName(trueName);
	userDTO.setTelephone(telephone);
	userDTO.setEmail(email);
	if(sex.equals("男")) {
	    userDTO.setSex((byte)0);
	}else {
	    userDTO.setSex((byte)1);
	}
	
	userServiceClient.saveUser(userDTO);
    }
    
    @RequestMapping(value = "loadUserList.do", produces = "text/html;charset=UTF-8")
    public String loadUserList(HttpServletRequest request,
	    HttpServletResponse response) {
	List<CityDTO> cityDTOList = new ArrayList<>();
	cityDTOList = siteServiceClient.getAllCity();
	
	request.setAttribute("cityDTOList", cityDTOList);
	
	return "pages/user_list";
    }
    
    @RequestMapping(value = "searchUsers.do", produces = "text/html;charset=UTF-8")
    public @ResponseBody String searchUsers(HttpServletRequest request,
	    HttpServletResponse response) {
	String username = request.getParameter("loginName");  // 用户名(登录名)
	String trueName = request.getParameter("trueName");  // 姓名
	int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 当前页号

	String result = userServiceClient.getUsersList(username, trueName, pageNum);

	return result;
    }
}
