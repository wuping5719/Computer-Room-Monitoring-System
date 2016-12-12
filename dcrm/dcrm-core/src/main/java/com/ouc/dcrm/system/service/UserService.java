package com.ouc.dcrm.system.service;

import com.ouc.dcrm.system.model.User;

public interface UserService {
    
    public void saveUser(User user);  //插入用户信息
    
    public User searchUserByPrimaryKey(Integer id);  //根据用户id查询用户信息
    
    public User searchUserByUserName(String username); // 根据用户名获取用户信息
    
}
