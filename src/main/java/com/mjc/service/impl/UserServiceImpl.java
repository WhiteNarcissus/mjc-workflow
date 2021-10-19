package com.mjc.service.impl;

import com.mjc.dao.UserDOMapper;
import com.mjc.dao.UserPasswordDOMapper;
import com.mjc.dataobject.UserDO;
import com.mjc.dataobject.UserPasswordDO;
import com.mjc.erro.BusinessException;
import com.mjc.erro.EmBussinessErr;
import com.mjc.service.UserService;
import com.mjc.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        return convertFromDataObject(userDO, userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBussinessErr.PARAMETER_VALIDATION_ERROR);
        }

        if (StringUtils.isBlank(userModel.getName()) || userModel.getGender() == null || StringUtils.isBlank(userModel.getTelphone()) || userModel.getAge() == null) {
            throw new BusinessException(EmBussinessErr.PARAMETER_VALIDATION_ERROR);
        }
//        usermodel -> dataObject
        UserDO userDO = convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }


    @Override
    public UserModel login(String telphone, String password) throws BusinessException {
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if(userDO == null || userPasswordDO ==null ){
            throw  new BusinessException(EmBussinessErr.USER_LOOGIN_FAIL);
        }
        return convertFromDataObject(userDO, userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null)
            userModel.setEncreptPassword(userPasswordDO.getEncreptPassword());
        return userModel;
    }


    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }


    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncreptPassword(userModel.getEncreptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }


}
