package com.capslock.rpc.api.user;

import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;

/**
 * Created by alvin.
 */
public interface UserService {
    void addUser(final User user, final RegisterInfo registerInfo);

    User findUser(MobileNumber mobileNumber);

    boolean userExists(MobileNumber mobileNumber);
}
