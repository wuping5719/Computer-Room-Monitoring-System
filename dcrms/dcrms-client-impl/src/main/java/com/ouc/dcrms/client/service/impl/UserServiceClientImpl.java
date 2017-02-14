package com.ouc.dcrms.client.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ouc.dcrms.client.dto.UserDTO;
import com.ouc.dcrms.client.service.UserServiceClient;
import com.ouc.dcrms.core.service.UserServiceCore;
import com.ouc.dcrms.core.service.RoleServiceCore;
import com.ouc.dcrms.core.model.User;

public class UserServiceClientImpl implements UserServiceClient {
    
    private UserServiceCore userServiceCore;

    private RoleServiceCore roleServiceCore;
    
    @Override
    public UserDTO getUserById(Integer id) {
	UserDTO userDTO = new UserDTO();
	User user = userServiceCore.searchUserById(id);
	userDTO.setId(user.getUserid());
	userDTO.setName(user.getName());
	return userDTO;
    }
    
    @Override
    public String saveUser(UserDTO userDTO) {
	User user = new User();
	user.setUserid(null);  //让ID自增
	user.setName(userDTO.getName());
	user.setPassword(userDTO.getPassword());
	user.setUsername(userDTO.getUsername());
	user.setSex(userDTO.getSex());
	user.setTelephone(userDTO.getTelephone());
	user.setEmail(userDTO.getEmail());
	
	System.out.println("用户注册：");
	System.out.println("姓名：" + userDTO.getUsername());
	
	userServiceCore.insertUser(user);
        return "success";
    }
 
    @Override
    public UserDTO getUserByUserName(String username) {
	UserDTO userDTO = new UserDTO();
	User user = userServiceCore.searchUserByUserName(username);
	userDTO.setId(user.getUserid());
	userDTO.setName(user.getName());
	userDTO.setUsername(user.getUsername());
	userDTO.setPassword(user.getPassword());
	userDTO.setTelephone(user.getTelephone());
	userDTO.setEmail(user.getEmail());
	return userDTO;
    }
    
    @Override
    public String getUserList(String loginName, String trueName, int pageNum) {
	int totalNum = 0;   // 总记录数
	int totalPage = 1;  // 总页数
	int pageSize = 20;
	int startIndex = 0; // 开始索引
	
	totalNum = userServiceCore.getTotalNum(loginName, trueName);
	
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
	usersList = userServiceCore.getUsers(loginName, trueName, startIndex, pageSize);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	int n = startIndex + 1;  // 设置序号
	JSONArray jsonArray = new JSONArray();
	for(User user: usersList) {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sortIndex", n);
	    jsonObject.put("loginName", user.getUsername());
	    jsonObject.put("trueName", user.getName());
	    jsonObject.put("email", user.getEmail());
	    jsonObject.put("mobilePhone", user.getTelephone());
	    if(user.getSex()==0) {
		jsonObject.put("sex", "男");
	    }else {
		jsonObject.put("sex", "女");
	    }
	    jsonObject.put("gmtModified", sdf.format(user.getGmtModified()));
	    jsonObject.put("id", user.getUserid());
	    
	    String roleName = roleServiceCore.selectByPrimaryKey(Integer.parseInt(user.getRoleid())).getRolename();
	    jsonObject.put("roleName", roleName);
	    
	    jsonArray.add(jsonObject);
	    n++;
	}

	Map<String, Object> result = new HashMap<String, Object>(3);
	result.put("userDTOsList", jsonArray);
	result.put("userTotalNum", totalNum);
	result.put("userTotalPage", totalPage);
	return JSONObject.fromObject(result).toString();
    }
    
    public UserServiceCore getUserServiceCore() {
	return userServiceCore;
    }

    public void setUserServiceCore(UserServiceCore userServiceCore) {
	this.userServiceCore = userServiceCore;
    }

    public RoleServiceCore getRoleServiceCore() {
	return roleServiceCore;
    }

    public void setRoleServiceCore(RoleServiceCore roleServiceCore) {
	this.roleServiceCore = roleServiceCore;
    }
    
}
