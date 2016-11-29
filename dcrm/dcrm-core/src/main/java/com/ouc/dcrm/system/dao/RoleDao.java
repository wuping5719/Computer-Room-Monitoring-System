package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Role;

public interface RoleDao {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}