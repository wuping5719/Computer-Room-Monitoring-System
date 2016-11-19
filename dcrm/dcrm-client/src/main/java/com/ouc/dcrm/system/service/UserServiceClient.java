package com.ouc.dcrm.system.service;

import com.ouc.dcrm.system.dto.UserDTO;

/**
 * @author WuPing
 */

public interface UserServiceClient {

    public UserDTO getUserById(Integer id);
    
    public String saveUser(UserDTO userDTO);
    
    public UserDTO getUserByLoginName(String loginName);
    
    public String getUsersList(String loginName, String trueName, int pageNum);
    
}
