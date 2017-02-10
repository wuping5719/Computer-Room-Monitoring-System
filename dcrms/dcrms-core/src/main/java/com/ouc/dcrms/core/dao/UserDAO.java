package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.User;

public interface UserDAO {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUserName(String username);
}