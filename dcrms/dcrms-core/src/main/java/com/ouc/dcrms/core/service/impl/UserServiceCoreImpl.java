package com.ouc.dcrms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ouc.dcrms.core.service.UserServiceCore;
import com.ouc.dcrms.core.dao.UserDAO;
import com.ouc.dcrms.core.model.User;

public class UserServiceCoreImpl implements UserServiceCore {
    
    private UserDAO userDAO;

    @Override
    public User searchUserById(Integer id) {
	User user = userDAO.selectByPrimaryKey(id);
	return user;
    }
    
    @Override
    public String insertUser(User user) {
	userDAO.insertSelective(user);
        return "success";
    }
 
    @Override
    public User searchUserByUserName(String username) {
	User user = userDAO.selectByUserName(username);
	return user;
    }
    
    @Override
    public int getTotalNum(String username, String name) {
	return userDAO.selectTotalNum(username, name);
    }
    
    @Override
    public List<User> getUsers(String username, String name,
	    int startIndex, int pageSize){
	List<User> userList = new ArrayList<>();
	userList = userDAO.selectUsers(username, name, startIndex, pageSize);
	return userList;
    }
    
    public UserDAO getUserDAO() {
	return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
	this.userDAO = userDAO;
    }
}
