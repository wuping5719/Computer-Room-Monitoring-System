package com.ouc.dcrm.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ouc.dcrm.system.model.User;

public interface UserDao {
    
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByByLoginName(String loginName);
    
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int selectTotalNum(@Param("login_name") String login_name,
	    @Param("true_name") String true_name);
    
    List<User> selectUsers(@Param("login_name") String login_name,
	    @Param("true_name") String true_name,
	    @Param("startIndex") int startIndex, 
	    @Param("pageSize") int pageSize);
    
}
