package com.ouc.dcrm.system.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ouc.dcrm.system.dto.UserDTO;
import com.ouc.dcrm.system.model.User;
import com.ouc.dcrm.system.service.UserServiceClient;
import com.ouc.dcrm.system.service.UserService;
import com.ouc.dcrm.system.util.PagingParameters;

public class UserServiceClientImpl implements UserServiceClient {

    private UserService userService;

    public UserService getUserService() {
	return userService;
    }

    public void setUserService(UserService userService) {
	this.userService = userService;
    }

    @Override
    public UserDTO getUserById(Integer id) {
	UserDTO userDTO = new UserDTO();
	User user = userService.searchUserByPrimaryKey(id);
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
	
	userService.saveUser(user);
        return "success";
    }
 
    @Override
    public UserDTO getUserByUserName(String username) {
	UserDTO userDTO = new UserDTO();
	User user = userService.searchUserByUserName(username);
	userDTO.setId(user.getUserid());
	userDTO.setName(user.getName());
	userDTO.setUsername(user.getUsername());
	userDTO.setPassword(user.getPassword());
	userDTO.setTelephone(user.getTelephone());
	userDTO.setEmail(user.getEmail());
	return userDTO;
    }
}
