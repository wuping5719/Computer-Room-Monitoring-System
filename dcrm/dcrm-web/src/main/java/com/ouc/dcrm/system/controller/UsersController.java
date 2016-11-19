package com.ouc.dcrm.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ouc.dcrm.system.dto.UserDTO;
import com.ouc.dcrm.system.service.UserServiceClient;

/*
 * @Author WuPing
 */

@Controller
public class UsersController {

    @Resource(name = "userServiceClient")
    private UserServiceClient userServiceClient;

    // ----------加载用户注册界面--------------
    @RequestMapping(value = "loadUserRegister.do")
    public String loadUserRegister(HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute("corpNameList", "");
	return "userRegisterHtml5";
    }

    // 用户注册
    @RequestMapping(value = "userRegister.do")
    public void userRegister(HttpServletRequest request,
	    HttpServletResponse response) {
	String username = request.getParameter("username");  // 用户名(登录名)
	String password = request.getParameter("password");  // 密码
	String trueName = request.getParameter("trueName");  // 姓名
	String telephone = request.getParameter("telephone");  // 手机
	String email = request.getParameter("email");      // 邮箱
	String sex = request.getParameter("sex");     // 性别

	UserDTO userDTO = new UserDTO();
	userDTO.setLogin_name(username);
	userDTO.setPassword(password);
	userDTO.setTrue_name(trueName);
	userDTO.setMobile_phone(telephone);
	userDTO.setEmail(email);
	if(sex.equals("男")) {
	    userDTO.setSex(0);
	}else {
	    userDTO.setSex(1);
	}
	
	userServiceClient.saveUser(userDTO);
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
