package com.ouc.dcrms.client.service;

/**
 * @author WuPing
 */

public interface AuthorityServiceClient {

    public String getRoleList(String roleName, String description, int pageNum);
    
    public String getResList(String resName, String resType, int pageNum);
}

