package com.capslock.rpc.service.user.updater;

import com.capslock.rpc.service.user.mapper.UserBlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

/**
 * Created by alvin.
 */
@Component
public class UserBlacklistUpdater {
    @Autowired
    private UserBlacklistMapper userBlacklistMapper;

    public Observable<Void> addUserIntoBlacklistAsync(final long ownerUid, final long userId) {
        return toRx(() -> addUserIntoBlacklist(ownerUid, userId));
    }

    public void addUserIntoBlacklist(final long ownerUid, final long userId) {
        userBlacklistMapper.addUserIntoBlacklist(ownerUid, userId);
    }

    public Observable<Void> removeUserInBlacklistAsync(final long ownerUid, final long userId) {
        return toRx(() -> removeUserInBlacklist(ownerUid, userId));
    }

    public void removeUserInBlacklist(final long ownerUid, final long userId) {
        userBlacklistMapper.removeUserInBlacklist(ownerUid, userId);
    }
}
