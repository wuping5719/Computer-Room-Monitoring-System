package com.ouc.dcrms.client.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ouc.dcrms.client.service.AuthorityServiceClient;
import com.ouc.dcrms.core.service.RoleServiceCore;
import com.ouc.dcrms.core.model.Role;

public class AuthorityServiceClientImpl implements AuthorityServiceClient {
    
    private RoleServiceCore roleServiceCore;
    
    @Override
    public String getRoleList(String roleName, String description, int pageNum) {
	int totalNum = 0;   // 总记录数
	int totalPage = 1;  // 总页数
	int pageSize = 20;
	int startIndex = 0; // 开始索引
	
	totalNum = roleServiceCore.getTotalNum(roleName, description);
	
	totalPage = totalNum % pageSize == 0 
		? totalNum / pageSize
		: totalNum / pageSize + 1;
	
	if (totalNum > 0) {
	    if (pageNum <= 1) {
		startIndex = 0;
	    } else {
		startIndex = pageNum * pageSize - pageSize;
	    }
	}
	
	List<Role> roleList = new ArrayList<>();
	roleList = roleServiceCore.getRoles(roleName, description, startIndex, pageSize);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	int n = startIndex + 1;  // 设置序号
	JSONArray jsonArray = new JSONArray();
	for(Role role: roleList) {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sortIndex", n);
	    jsonObject.put("roleName", role.getRolename());
	    jsonObject.put("description", role.getDescription());
	    jsonObject.put("gmtCreate", sdf.format(role.getGmtCreate()));
	    jsonObject.put("gmtModified", sdf.format(role.getGmtModified()));
	    jsonObject.put("createBy", role.getCreateBy());
	    jsonObject.put("lastModifedBy", role.getLastModifedBy());
	    jsonObject.put("roleId", role.getRoleid());
	    jsonArray.add(jsonObject);
	    n++;
	}

	Map<String, Object> result = new HashMap<String, Object>(3);
	result.put("roleDTOsList", jsonArray);
	result.put("roleTotalNum", totalNum);
	result.put("roleTotalPage", totalPage);
	return JSONObject.fromObject(result).toString();
    }

    public RoleServiceCore getRoleServiceCore() {
	return roleServiceCore;
    }

    public void setRoleServiceCore(RoleServiceCore roleServiceCore) {
	this.roleServiceCore = roleServiceCore;
    }
    
}
