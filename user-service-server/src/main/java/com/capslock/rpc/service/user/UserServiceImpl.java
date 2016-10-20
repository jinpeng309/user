package com.capslock.rpc.service.user;

import com.capslock.rpc.api.user.UserService;
import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.service.user.mapper.RegisterInfoMapper;
import com.capslock.rpc.service.user.mapper.UserMapper;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alvin.
 */
@MotanService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegisterInfoMapper registerInfoMapper;

    @Override
    public void addUser(final User user, final RegisterInfo registerInfo) {
        userMapper.addUser(user);
        registerInfoMapper.addRegisterInfo(registerInfo);
    }

    @Override
    public User findUser(final MobileNumber mobileNumber) {
        return userMapper.findByPhoneNumber(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber());
    }

    @Override
    public boolean userExists(final MobileNumber mobileNumber) {
        return userMapper.findByPhoneNumber(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber()) != null;
    }
}
