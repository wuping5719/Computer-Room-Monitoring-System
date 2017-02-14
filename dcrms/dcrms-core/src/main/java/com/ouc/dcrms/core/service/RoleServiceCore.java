package com.ouc.dcrms.core.service;

import java.util.List;

import com.ouc.dcrms.core.model.Role;

/**
 * @author WuPing
 * @version 2017年2月10日 下午7:59:27
 */

public interface RoleServiceCore {
    
    public Role selectByPrimaryKey(Integer roleid);
    
    public int getTotalNum(String roleName, String description);

    public List<Role> getRoles(String roleName, String description,
	    int startIndex, int pageSize);
}

