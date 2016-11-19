package com.ouc.dcrm.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ouc.dcrm.system.dto.UserDTO;
import com.ouc.dcrm.system.service.UserServiceClient;
import com.ouc.dcrm.system.utils.SessionConstants;

//类似Struts的Action
@Controller
public class LoginController {

    @Resource(name = "userServiceClient")
    private UserServiceClient userServiceClient;

    /**********************
     * 登录验证
     ***********************/
    @RequestMapping(value = "login.do")
    public @ResponseBody
    String login(HttpServletRequest request, HttpServletResponse response) {
	// 获取前台传来的用户名和密码
	String username = request.getParameter("username");
	String passWord = request.getParameter("password"); // 明文密码
	System.out.println("当前登录用户：" + username);

	// 设置登录反馈信息变量：1—成功；2—用户名不存在；3—密码无效登录失败。
	String msg = null;
	UserDTO userDTO = userServiceClient.getUserByLoginName(username);
	if(userDTO == null) {
	    msg="2";
	}else {
	    if(passWord.equals(userDTO.getPassword())){
		request.getSession().setAttribute(SessionConstants.KEY_USER_ID, userDTO.getId().toString());
		request.getSession().setAttribute(SessionConstants.KEY_USER_NAME, username);
		request.getSession().setAttribute(SessionConstants.KEY_True_NAME, userDTO.getTrue_name());
		msg="1";
	    }else {
		msg="3";
	    }
	}
	return msg;
    }

    // 请求url地址映射，类似Struts的action-mapping
    @RequestMapping(value = "loadHome.do", method = RequestMethod.GET)
    public String loadHome(HttpServletRequest request,
	    HttpServletResponse response) {
	String userType = request.getParameter("userType");
	System.out.println(userType);
	return "pages/authority/auth_main";
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
	request.getSession().invalidate();
	return "redirect:/login.jsp";
    }

}
