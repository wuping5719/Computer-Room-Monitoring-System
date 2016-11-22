package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.User;

public interface UserDao {
    
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
}
