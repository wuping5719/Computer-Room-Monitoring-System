package com.ouc.dcrms.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ouc.dcrms.core.model.User;

public interface UserDAO {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUserName(String username);
    
    int selectTotalNum(@Param("username") String username,
	    @Param("name") String name);
    
    List<User> selectUsers(@Param("username") String username,
	    @Param("name") String name,
	    @Param("startIndex") int startIndex, 
	    @Param("pageSize") int pageSize);
}