package com.ouc.dcrms.core.service;

import java.util.List;

import com.ouc.dcrms.core.model.User;

/**
 * @author WuPing
 */

public interface UserServiceCore {

    public User searchUserById(Integer id);
    
    public String insertUser(User user);
    
    public User searchUserByUserName(String username);
    
    public int getTotalNum(String username, String name);

    public List<User> getUsers(String username, String name,
	    int startIndex, int pageSize);
}

