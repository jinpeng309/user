package com.capslock.rpc.service.user.extractor;

import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.service.user.repository.mapper.model.UserInfoCacheData;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by alvin.
 */
@Component
public class UserInfoCacheExtractor {

    public UserInfoCacheData extract(final User user) {
        final UserInfoCacheData cacheData = new UserInfoCacheData(user.getUserId());
        cacheData.setAvatar(Optional.of(user.getAvatar()));
        return cacheData;
    }
}
