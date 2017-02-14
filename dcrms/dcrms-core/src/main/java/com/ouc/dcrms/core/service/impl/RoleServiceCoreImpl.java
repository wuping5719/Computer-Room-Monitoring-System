package com.ouc.dcrms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ouc.dcrms.core.model.Role;
import com.ouc.dcrms.core.service.RoleServiceCore;
import com.ouc.dcrms.core.dao.RoleDAO;

public class RoleServiceCoreImpl implements RoleServiceCore {
    
    private RoleDAO roleDAO;

    @Override
    public Role selectByPrimaryKey(Integer roleid) {
	Role role = roleDAO.selectByPrimaryKey(roleid);
	return role;
    }

    @Override
    public int getTotalNum(String roleName, String description) {
	return roleDAO.selectTotalNum(roleName, description);
    }
    
    @Override
    public List<Role> getRoles(String roleName, String description,
	    int startIndex, int pageSize){
	List<Role> roleList = new ArrayList<>();
	roleList = roleDAO.selectRoles(roleName, description, startIndex, pageSize);
	return roleList;
    }
    
    public RoleDAO getRoleDAO() {
	return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
	this.roleDAO = roleDAO;
    }
    
}
