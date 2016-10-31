package com.capslock.rpc.service.user;

import com.capslock.rpc.api.seq.SeqGeneratorService;
import com.capslock.rpc.api.user.UserService;
import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.assembler.UserInfoAssembler;
import com.capslock.rpc.service.user.factory.UserInfoCacheDataFactory;
import com.capslock.rpc.service.user.fetcher.UserBlacklistFetcher;
import com.capslock.rpc.service.user.fetcher.UserFetcher;
import com.capslock.rpc.service.user.fetcher.UserInfoFetcher;
import com.capslock.rpc.service.user.mapper.UserInfoDeltaMapper;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import com.capslock.rpc.service.user.updater.RegisterInfoUpdater;
import com.capslock.rpc.service.user.updater.UserBlacklistUpdater;
import com.capslock.rpc.service.user.updater.UserInfoUpdater;
import com.capslock.rpc.service.user.updater.UserUpdater;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by alvin.
 */
@MotanService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoCacheDataFactory userInfoCacheDataFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserInfoDeltaMapper userInfoDeltaMapper;
    @MotanReferer(basicReferer = "basicConfig", group = "seq-service-rpc")
    private SeqGeneratorService seqGeneratorService;

    @Autowired
    private UserInfoUpdater userInfoUpdater;
    @Autowired
    private UserInfoFetcher userInfoFetcher;
    @Autowired
    private UserUpdater userUpdater;
    @Autowired
    private UserFetcher userFetcher;
    @Autowired
    private UserBlacklistUpdater userBlacklistUpdater;
    @Autowired
    private UserBlacklistFetcher userBlacklistFetcher;
    @Autowired
    private RegisterInfoUpdater registerInfoUpdater;
    @Autowired
    private UserInfoAssembler userInfoAssembler;

    @Override
    public void addUser(final User user, final RegisterInfo registerInfo) throws IOException {
        Observable.zip(userUpdater.addUser(user),
                registerInfoUpdater.addRegisterInfo(registerInfo),
                userInfoUpdater.addUserInfo(userInfoCacheDataFactory.createFromUser(user, 1)), (r1, r2, r3) -> {
                    return null;
                }).toBlocking().single();
    }

    @Override
    public User fetchUser(final MobileNumber mobileNumber) {
        return userFetcher.fetchUserByPhoneNumber(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber());
    }

    @Override
    public boolean isUserExists(final MobileNumber mobileNumber) {
        return userFetcher.fetchUserByPhoneNumber(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber()) != null;
    }

    @Override
    public UserInfo fetchUserInfo(final long ownerUid, final long userId) throws IOException {
        return userInfoFetcher.fetchUserInfo(ownerUid, userId);
    }

    @Override
    public void addUserIntoBlacklist(final long ownerUid, final long userId) throws IOException {
        userBlacklistUpdater.addUserIntoBlacklist(ownerUid, userId);
        userInfoFetcher.fetchUserInfoAsync(ownerUid, userId);
//        final String data = objectMapper.writeValueAsString(userInfo);
//        final SeqGeneratorService.Result result = seqGeneratorService.generateSeq(userId, 1);
//        final long seq = result.getSequence();
//        userInfoDeltaMapper.addDelta(ownerUid, data, seq);
    }

    @Override
    public List<Long> fetchUserBlacklist(final long ownerUid) {
        return userBlacklistFetcher.fetchUserBlacklist(ownerUid);
    }

    @Override
    public void removeUserInBlacklist(final long ownerUid, final long userId) {
        userBlacklistUpdater.removeUserInBlacklistAsync(ownerUid, userId).toBlocking().single();
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
