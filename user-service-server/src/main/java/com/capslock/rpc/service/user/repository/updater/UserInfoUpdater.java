package com.capslock.rpc.service.user.repository.updater;

import com.capslock.rpc.service.user.repository.mapper.UserInfoMapper;
import com.capslock.rpc.service.user.repository.mapper.model.UserInfoCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

/**
 * Created by alvin.
 */
@Component
public class UserInfoUpdater {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public Observable<Void> addUserInfo(final UserInfoCacheData userInfoCacheData) {
        return toRx(() -> userInfoMapper.addUserInfo(userInfoCacheData));
    }
}
