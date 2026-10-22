package com.instantpay.app.user.services;

import java.util.UUID;

import com.instantpay.app.user.dtos.CreateUserDTO;
import com.instantpay.app.user.dtos.UpdateUserDTO;
import com.instantpay.app.user.dtos.UserDTO;

public interface UserService {
    
    public UserDTO findById(UUID id);
    public void createUser(CreateUserDTO data);
    public void updateUser(UUID id, UpdateUserDTO data);
    public void closeAccount(UUID id);
}
