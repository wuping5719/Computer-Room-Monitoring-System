package com.ouc.dcrms.client.service.impl;

import com.ouc.dcrms.client.dto.UserDTO;
import com.ouc.dcrms.client.service.UserServiceClient;
import com.ouc.dcrms.core.service.UserServiceCore;
import com.ouc.dcrms.core.model.User;

public class UserServiceClientImpl implements UserServiceClient {
    
    private UserServiceCore userServiceCore;

    public UserServiceCore getUserServiceCore() {
	return userServiceCore;
    }

    public void setUserServiceCore(UserServiceCore userServiceCore) {
	this.userServiceCore = userServiceCore;
    }
    
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
}
