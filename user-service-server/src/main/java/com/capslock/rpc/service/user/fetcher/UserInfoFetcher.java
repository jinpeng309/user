package com.capslock.rpc.service.user.fetcher;

import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.assembler.UserInfoAssembler;
import com.capslock.rpc.service.user.mapper.UserInfoMapper;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

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
        return userInfoAssembler.assemble(fetchUserInfoCacheDataAsync(userId), userFetcher.fetchUserByIdAsync(userId),
                userBlacklistFetcher.isInBlacklistAsync(ownerUid, userId));
    }

    public UserInfo fetchUserInfo(final long ownerUid, final long userId) {
        return fetchUserInfoAsync(ownerUid, userId).toBlocking().single();
    }

    public Observable<UserInfoCacheData> fetchUserInfoCacheDataAsync(final long userId) {
        return  Observable.fromCallable(() -> userInfoMapper.fetchUserInfo(userId));
    }
}
