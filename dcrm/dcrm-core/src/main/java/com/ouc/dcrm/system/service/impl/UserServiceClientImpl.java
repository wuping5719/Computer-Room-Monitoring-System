package com.ouc.dcrm.system.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ouc.dcrm.system.dao.UserDao;
import com.ouc.dcrm.system.dto.UserDTO;
import com.ouc.dcrm.system.model.User;
import com.ouc.dcrm.system.service.UserServiceClient;
import com.ouc.dcrm.system.util.PagingParameters;

public class UserServiceClientImpl implements UserServiceClient {

    private UserDao userDao;

    public UserDao getUserDao() {
	return userDao;
    }

    public void setUserDao(UserDao userDao) {
	this.userDao = userDao;
    }

    @Override
    public UserDTO getUserById(Integer id) {
	UserDTO userDTO = new UserDTO();
	User user = userDao.selectByPrimaryKey(id);
	userDTO.setId(user.getId());
	userDTO.setTrue_name(user.getTrue_name());
	return userDTO;
    }
    
    @Override
    public String saveUser(UserDTO userDTO) {
	User user = new User();
	user.setId(null);  //让ID自增
	user.setLogin_name(userDTO.getLogin_name());
	user.setPassword(userDTO.getPassword());
	user.setTrue_name(userDTO.getTrue_name());
	user.setSex(userDTO.getSex());
	user.setMobile_phone(userDTO.getMobile_phone());
	user.setEmail(userDTO.getEmail());
	
	System.out.println("用户注册：");
	System.out.println("姓名：" + userDTO.getTrue_name());
	
	InetAddress inet = null;
	try {
	    inet = InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}      
        String ip = inet.getHostAddress();
        System.out.println("本机的IP:" + ip); 
        user.setCurrent_login_ip(ip);
        user.setLast_login_ip(ip);
        
        user.setLogin_attempt_times(1);
        Date today = new Date();
        user.setLogin_faild_time(today);
        user.setGmt_create(today);
        user.setGmt_modified(today);
        user.setCreate_by("admin");
        user.setLast_modified_by("admin");
        user.setPassword_first_modified_flag(1);
        
        userDao.insertSelective(user);
        return "success";
    }
    
    @Override
    public UserDTO getUserByLoginName(String loginName) {
	UserDTO userDTO = new UserDTO();
	try {
	    User user = new User();
	    user = userDao.selectByByLoginName(loginName);
	    if(user != null) {
		userDTO.setId(user.getId());
		userDTO.setLogin_name(user.getLogin_name());
		userDTO.setPassword(user.getPassword());
		userDTO.setTrue_name(user.getTrue_name());
		userDTO.setMobile_phone(user.getMobile_phone());
		userDTO.setEmail(user.getEmail());
	    }
	}catch(Exception e) {
	    e.printStackTrace();
	}
	return userDTO;
    }
    
    @Override
    public String getUsersList(String loginName, String trueName, int pageNum) {
	int totalNum = 0;   // 总记录数
	int totalPage = 0;  // 总页数
	int pageSize = PagingParameters.PAGE_SIZE;
	int startIndex = 0; // 开始索引
	
	totalNum = userDao.selectTotalNum(loginName, trueName);
	
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
	
	List<User> usersList = new ArrayList<>();
	usersList = userDao.selectUsers(loginName, trueName, startIndex, pageSize);

	int n = startIndex + 1;  // 设置序号
	JSONArray jsonArray = new JSONArray();
	for(User user: usersList) {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sortIndex", n);
	    jsonObject.put("loginName", user.getLogin_name());
	    jsonObject.put("trueName", user.getTrue_name());
	    jsonObject.put("email", user.getEmail());
	    jsonObject.put("mobilePhone", user.getMobile_phone());
	    if(user.getSex()==0) {
		jsonObject.put("sex", "男");
	    }else {
		jsonObject.put("sex", "女");
	    }
	    jsonObject.put("id", user.getId());
	    jsonArray.add(jsonObject);
	    n++;
	}

	Map<String, Object> result = new HashMap<String, Object>(3);
	result.put("userDTOsList", jsonArray);
	result.put("userTotalNum", totalNum);
	result.put("userTotalPage", totalPage);
	return JSONObject.fromObject(result).toString();
    }
}
