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
import com.ouc.dcrms.core.service.ResourceServiceCore;
import com.ouc.dcrms.core.model.Role;
import com.ouc.dcrms.core.model.Resources;

public class AuthorityServiceClientImpl implements AuthorityServiceClient {
    
    private RoleServiceCore roleServiceCore;
    
    private ResourceServiceCore resourceServiceCore;
    
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
    
    @Override
    public String getResList(String resName, String resType, int pageNum) {
	int totalNum = 0;   // 总记录数
	int totalPage = 1;  // 总页数
	int pageSize = 20;
	int startIndex = 0; // 开始索引
	
	Byte type = -1;
	if("URL资源".equals(resType)) {
	    type = 0;
	}else if("组件资源".equals(resType)) {
	    type = 1;
	}else if("虚拟资源".equals(resType)) {
	    type = 2;
	}else {
	    type = -1;
	}
	
	totalNum = resourceServiceCore.getTotalNum(resName, type);
	
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
	
	List<Resources> resList = new ArrayList<>();
	resList = resourceServiceCore.getResources(resName, type, startIndex, pageSize);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	int n = startIndex + 1;  // 设置序号
	JSONArray jsonArray = new JSONArray();
	for(Resources res: resList) {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("sortIndex", n);
	    jsonObject.put("resName", res.getName());
	    jsonObject.put("description", res.getDescription());
	    jsonObject.put("url", res.getUrl());
	    if (res.getType() == 1) {
		jsonObject.put("resType", "组件资源");
	    }else if (res.getType() == 2) {
		jsonObject.put("resType", "虚拟资源");
	    }else {
		jsonObject.put("resType", "URL资源");
	    }
	    jsonObject.put("gmtCreate", sdf.format(res.getGmtCreate()));
	    jsonObject.put("gmtModified", sdf.format(res.getGmtModified()));
	    jsonObject.put("resId", res.getResid());
	    jsonArray.add(jsonObject);
	    n++;
	}

	Map<String, Object> result = new HashMap<String, Object>(3);
	result.put("resDTOsList", jsonArray);
	result.put("resTotalNum", totalNum);
	result.put("resTotalPage", totalPage);
	return JSONObject.fromObject(result).toString();
    }
    
    public RoleServiceCore getRoleServiceCore() {
	return roleServiceCore;
    }

    public void setRoleServiceCore(RoleServiceCore roleServiceCore) {
	this.roleServiceCore = roleServiceCore;
    }

    public ResourceServiceCore getResourceServiceCore() {
	return resourceServiceCore;
    }

    public void setResourceServiceCore(ResourceServiceCore resourceServiceCore) {
	this.resourceServiceCore = resourceServiceCore;
    }
    
}
