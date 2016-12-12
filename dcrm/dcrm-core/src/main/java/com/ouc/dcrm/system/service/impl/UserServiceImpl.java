package com.ouc.dcrm.system.service.impl;

import com.ouc.dcrm.system.dao.UserDao;
import com.ouc.dcrm.system.model.User;
import com.ouc.dcrm.system.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
	return userDao;
    }

    public void setUserDao(UserDao userDao) {
	this.userDao = userDao;
    }

    @Override
    public User searchUserByPrimaryKey(Integer id) {
	User user = userDao.selectByPrimaryKey(id);
	return user;
    }
    
    @Override
    public void saveUser(User user) {
        userDao.insertSelective(user);
    }
 
    @Override
    public User searchUserByUserName(String username) {
	User user = userDao.selectByUserName(username);
	return user;
    }
}
