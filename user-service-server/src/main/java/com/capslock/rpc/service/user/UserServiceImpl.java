package com.capslock.rpc.service.user;

import com.capslock.rpc.api.user.UserService;
import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.assembler.UserInfoAssembler;
import com.capslock.rpc.service.user.factory.UserInfoCacheDataFactory;
import com.capslock.rpc.service.user.mapper.RegisterInfoMapper;
import com.capslock.rpc.service.user.mapper.UserInfoMapper;
import com.capslock.rpc.service.user.mapper.UserMapper;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by alvin.
 */
@MotanService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegisterInfoMapper registerInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserInfoCacheDataFactory userInfoCacheDataFactory;
    @Autowired
    private UserInfoAssembler userInfoAssembler;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addUser(final User user, final RegisterInfo registerInfo) throws IOException {
        userMapper.addUser(user);
        registerInfoMapper.addRegisterInfo(registerInfo);
        userInfoMapper.addUserInfo(userInfoCacheDataFactory.createFromUser(user, 1));
    }

    @Override
    public User fetchUser(final MobileNumber mobileNumber) {
        return userMapper.findByPhoneNumber(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber());
    }

    @Override
    public boolean isUserExists(final MobileNumber mobileNumber) {
        return userMapper.findByPhoneNumber(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber()) != null;
    }

    @Override
    public UserInfo fetchUserInfo(final long userId) throws IOException {
        final UserInfoCacheData cacheData = userInfoMapper.fetchUserInfo(userId);
        final User user = userMapper.findByUserId(userId);
        return userInfoAssembler.assemble(cacheData, user);
    }

    @Override
    public String fetchUserInfoPatch(final long userId, final long oldVersion, final long targetVersion) throws IOException {
        final String oldRawData = userInfoMapper.fetchUserInfoRawData(userId, oldVersion);
        final String targetRawData = userInfoMapper.fetchUserInfoRawData(userId, targetVersion);
        final JsonNode oldJson = objectMapper.readTree(oldRawData);
        final JsonNode targetJson = objectMapper.readTree(targetRawData);
        return objectMapper.writeValueAsString(JsonDiff.asJson(oldJson, targetJson));
    }
}
