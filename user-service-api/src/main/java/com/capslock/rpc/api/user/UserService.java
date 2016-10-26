package com.capslock.rpc.api.user;

import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;

import java.io.IOException;

/**
 * Created by alvin.
 */
public interface UserService {
    void addUser(final User user, final RegisterInfo registerInfo) throws IOException;

    User fetchUser(final MobileNumber mobileNumber);

    boolean isUserExists(final MobileNumber mobileNumber);

    UserInfo fetchUserInfo(final long userId) throws IOException;

    String fetchUserInfoPatch(final long userId, final long oldVersion, final long targetVersion) throws IOException;
}
