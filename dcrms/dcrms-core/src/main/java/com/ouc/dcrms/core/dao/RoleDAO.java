package com.ouc.dcrms.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ouc.dcrms.core.model.Role;

public interface RoleDAO {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    int selectTotalNum(@Param("rolename") String rolename,
	    @Param("description") String description);
    
    List<Role> selectRoles(@Param("rolename") String rolename,
	    @Param("description") String description,
	    @Param("startIndex") int startIndex, 
	    @Param("pageSize") int pageSize);
}