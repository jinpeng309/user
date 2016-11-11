package com.capslock.rpc.service.user.repository.fetcher;

import com.capslock.rpc.service.user.repository.mapper.UserBlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class UserBlacklistFetcher {
    @Autowired
    private UserBlacklistMapper userBlacklistMapper;

    public Observable<Boolean> isInBlacklistAsync(final long ownerUid, final long userId) {
        return Observable.fromCallable(() -> isInBlacklist(ownerUid, userId));
    }

    public boolean isInBlacklist(final long ownerUid, final long userId) {
        return userBlacklistMapper.isInBlacklist(ownerUid, userId);
    }

    public Observable<List<Long>> fetchUserBlacklistAsync(final long ownerUid) {
        return Observable.fromCallable(() -> userBlacklistMapper.fetchUserBlacklist(ownerUid));
    }

    public List<Long> fetchUserBlacklist(final long ownerUid) {
        return userBlacklistMapper.fetchUserBlacklist(ownerUid);
    }
}
