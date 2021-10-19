package com.mjc.service;

import com.mjc.erro.BusinessException;
import com.mjc.service.model.UserModel;

public interface UserService {

    public UserModel getUserById(Integer id);

    public void register(UserModel userModel) throws BusinessException;

    public UserModel login(String telphone , String password) throws BusinessException;
}
