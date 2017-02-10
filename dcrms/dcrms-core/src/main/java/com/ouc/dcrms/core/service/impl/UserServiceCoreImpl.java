package com.ouc.dcrms.core.service.impl;

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
    
    public UserDAO getUserDAO() {
	return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
	this.userDAO = userDAO;
    }
}
