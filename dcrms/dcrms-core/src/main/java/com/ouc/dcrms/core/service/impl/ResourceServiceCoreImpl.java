package com.ouc.dcrms.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ouc.dcrms.core.model.Resources;
import com.ouc.dcrms.core.service.ResourceServiceCore;
import com.ouc.dcrms.core.dao.ResourcesDAO;

public class ResourceServiceCoreImpl implements ResourceServiceCore {
    
    private ResourcesDAO resourcesDAO;

    @Override
    public Resources selectByPrimaryKey(Integer resid){
	Resources res = resourcesDAO.selectByPrimaryKey(resid);
	return res;
    }

    @Override
    public int getTotalNum(String resName, Byte type) {
	return resourcesDAO.selectTotalNum(resName, type);
    }
    
    @Override
    public List<Resources> getResources(String resName, Byte type,
	    int startIndex, int pageSize) {
	List<Resources> resList = new ArrayList<>();
	resList = resourcesDAO.selectResources(resName, type, startIndex, pageSize);
	return resList;
    }
    
    public ResourcesDAO getResourcesDAO() {
	return resourcesDAO;
    }

    public void setResourcesDAO(ResourcesDAO resourcesDAO) {
	this.resourcesDAO = resourcesDAO;
    }
    
}
