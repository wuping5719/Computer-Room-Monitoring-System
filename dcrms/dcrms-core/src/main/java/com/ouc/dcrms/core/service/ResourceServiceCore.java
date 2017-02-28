package com.ouc.dcrms.core.service;

import java.util.List;

import com.ouc.dcrms.core.model.Resources;

/**
 * @author WuPing
 * @version 2017年2月10日 下午7:59:27
 */

public interface ResourceServiceCore {
    
    public Resources selectByPrimaryKey(Integer resid);
    
    public int getTotalNum(String resName, Byte type);

    public List<Resources> getResources(String resName, Byte type,
	    int startIndex, int pageSize);
    
    public List<Resources> getAllResource();
}

