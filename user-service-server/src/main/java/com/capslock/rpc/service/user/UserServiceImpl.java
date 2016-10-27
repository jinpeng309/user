package com.capslock.rpc.service.user;

import com.capslock.rpc.api.seq.SeqGeneratorService;
import com.capslock.rpc.api.user.UserService;
import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.assembler.UserInfoAssembler;
import com.capslock.rpc.service.user.factory.UserInfoCacheDataFactory;
import com.capslock.rpc.service.user.mapper.UserBlacklistMapper;
import com.capslock.rpc.service.user.mapper.RegisterInfoMapper;
import com.capslock.rpc.service.user.mapper.UserInfoDeltaMapper;
import com.capslock.rpc.service.user.mapper.UserInfoMapper;
import com.capslock.rpc.service.user.mapper.UserMapper;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private UserBlacklistMapper userBlacklistMapper;
    @Autowired
    private UserInfoDeltaMapper userInfoDeltaMapper;
    @MotanReferer(basicReferer = "basicConfig", group = "seq-service-rpc")
    private SeqGeneratorService seqGeneratorService;

    @PostConstruct
    public void foo(){
        SeqGeneratorService.Result result = seqGeneratorService.generateSeq(1, 0);
        System.out.println(result);
    }

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
    public UserInfo fetchUserInfo(final long ownerUid, final long userId) throws IOException {
        final UserInfoCacheData cacheData = userInfoMapper.fetchUserInfo(userId);
        final User user = userMapper.findByUserId(userId);
        final boolean isInBlacklist = userBlacklistMapper.isInBlacklist(ownerUid, userId);
        return userInfoAssembler.assemble(cacheData, user, isInBlacklist);
    }

    @Override
    public void addUserIntoBlacklist(final long ownerUid, final long userId) throws IOException {
        final UserInfoCacheData cacheData = userInfoMapper.fetchUserInfo(userId);
        final User user = userMapper.findByUserId(userId);
        final UserInfo userInfo = userInfoAssembler.assemble(cacheData, user, true);
        userBlacklistMapper.addUserIntoBlacklist(ownerUid, userId);
        final String data = objectMapper.writeValueAsString(userInfo);
        final SeqGeneratorService.Result result = seqGeneratorService.generateSeq(userId, 1);
        final long seq = result.getSequence();
        userInfoDeltaMapper.addDelta(ownerUid, data, seq);
    }

    @Override
    public List<Long> fetchUserBlacklist(final long ownerUid) {
        return userBlacklistMapper.fetchUserBlacklist(ownerUid);
    }

    @Override
    public void removeUserInBlacklist(final long ownerUid, final long userId) {
        userBlacklistMapper.removeUserInBlacklist(ownerUid, userId);
    }

    public static void main(String[] args) throws IOException {
        final UserInfoCacheData userInfoCacheData = new UserInfoCacheData(1, 1);
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module());
        ObjectReader reader = objectMapper.readerForUpdating(userInfoCacheData);
        final UserInfoCacheData newCacheData = new UserInfoCacheData(1, 2);
        newCacheData.setAvatar(Optional.of("asf"));
        final UserInfoCacheData merged = reader.readValue(objectMapper.writeValueAsString(newCacheData));
        System.out.println(merged);
    }
}
