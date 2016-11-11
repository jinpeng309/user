package com.capslock.rpc.service.user.repository.fetcher;

import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.assembler.UserInfoAssembler;
import com.capslock.rpc.service.user.repository.mapper.UserInfoMapper;
import com.capslock.rpc.service.user.repository.mapper.model.UserInfoCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class UserInfoFetcher {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserBlacklistFetcher userBlacklistFetcher;
    @Autowired
    private UserFetcher userFetcher;
    @Autowired
    private UserInfoAssembler userInfoAssembler;

    public Observable<UserInfo> fetchUserInfoAsync(final long ownerUid, final long userId) {
        return userInfoAssembler.assemble(fetchUserInfoCacheDataAsync(userId), userFetcher.fetchUserAsync(userId),
                userBlacklistFetcher.isInBlacklistAsync(ownerUid, userId));
    }

    public UserInfo fetchUserInfo(final long ownerUid, final long userId) {
        return fetchUserInfoAsync(ownerUid, userId).toBlocking().single();
    }

    public Observable<UserInfoCacheData> fetchUserInfoCacheDataAsync(final long userId) {
        return Observable.fromCallable(() -> userInfoMapper.fetchUserInfo(userId));
    }

    public Observable<List<UserInfoCacheData>> fetchUserInfoCacheDataListAsync(final List<Long> userIds) {
        return Observable.fromCallable(() -> userInfoMapper.fetchUserInfoList(userIds));
    }


    public List<UserInfoCacheData> fetchUserInfoCacheDataList(final List<Long> userIds) {
        return userInfoMapper.fetchUserInfoList(userIds);
    }

    public List<UserInfo> fetchUserInfoList(final long ownerUid, final List<Long> userIds) {
        return fetchUserInfoListAsync(ownerUid, userIds).toBlocking().single();
    }

    public Observable<List<UserInfo>> fetchUserInfoListAsync(final long ownerUid, final List<Long> userIds) {
        return userInfoAssembler.assembleUserInfoList(fetchUserInfoCacheDataListAsync(userIds),
                userFetcher.fetchUsersByUidListAsync(userIds), userBlacklistFetcher.fetchUserBlacklistAsync(ownerUid));
    }
}
