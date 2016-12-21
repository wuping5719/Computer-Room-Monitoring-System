package com.ouc.dcrms.core.service;

import com.ouc.dcrms.core.model.User;

/**
 * @author WuPing
 */

public interface UserServiceCore {

    public User searchUserById(Integer id);
    
    public String insertUser(User user);
    
    public User searchUserByUserName(String username);
}

