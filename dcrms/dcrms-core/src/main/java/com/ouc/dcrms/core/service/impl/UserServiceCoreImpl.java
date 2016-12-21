package com.ouc.dcrms.core.service.impl;

import com.ouc.dcrms.core.service.UserServiceCore;
import com.ouc.dcrms.core.dao.UserDao;
import com.ouc.dcrms.core.model.User;

public class UserServiceCoreImpl implements UserServiceCore {
    
    private UserDao userDao;

    public UserDao getUserDao() {
	return userDao;
    }

    public void setUserDao(UserDao userDao) {
	this.userDao = userDao;
    }

    @Override
    public User searchUserById(Integer id) {
	User user = userDao.selectByPrimaryKey(id);
	return user;
    }
    
    @Override
    public String insertUser(User user) {
	userDao.insertSelective(user);
        return "success";
    }
 
    @Override
    public User searchUserByUserName(String username) {
	User user = userDao.selectByUserName(username);
	return user;
    }
}
