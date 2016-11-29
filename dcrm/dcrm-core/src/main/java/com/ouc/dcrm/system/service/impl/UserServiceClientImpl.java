package com.ouc.dcrm.system.service.impl;

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
	
        userDao.insertSelective(user);
        return "success";
    }
 
}
