package com.ouc.dcrms.client.service;

import com.ouc.dcrms.client.dto.UserDTO;

/**
 * @author WuPing
 */

public interface UserServiceClient {

    public UserDTO getUserById(Integer id);
    
    public String saveUser(UserDTO userDTO);
    
    public UserDTO getUserByUserName(String username);
}

