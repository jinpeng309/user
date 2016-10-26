package com.capslock.rpc.service.user.factory;

import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by alvin.
 */
@Component
public class UserInfoCacheDataFactory {

    public UserInfoCacheData createFromUser(final User user, final long version) {
        final UserInfoCacheData cacheData = new UserInfoCacheData(user.getUserId(), version);
        cacheData.setAvatar(Optional.of(user.getAvatar()));
        return cacheData;
    }
}
