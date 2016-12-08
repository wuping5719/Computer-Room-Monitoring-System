package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUserName(String username);
}