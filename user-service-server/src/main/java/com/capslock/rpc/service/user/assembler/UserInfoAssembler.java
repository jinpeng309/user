package com.capslock.rpc.service.user.assembler;

import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import org.springframework.stereotype.Component;

/**
 * Created by alvin.
 */
@Component
public class UserInfoAssembler {

    public UserInfo assemble(final UserInfoCacheData cacheData, final User user, final boolean isInBlacklist) {
        final MobileNumber mobileNumber = new MobileNumber(user.getCountryCode(), user.getPhoneNumber());
        final UserInfo userInfo = new UserInfo(cacheData.getUserId(), mobileNumber);
        userInfo.setAvatar(cacheData.getAvatar());
        userInfo.setNickname(cacheData.getNickname());
        userInfo.setInBlackList(isInBlacklist);
        return userInfo;
    }
}
