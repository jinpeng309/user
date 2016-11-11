package com.capslock.rpc.api.user;

import com.capslock.rpc.api.user.model.BlackItem;
import com.capslock.rpc.api.user.model.Contact;
import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by alvin.
 */
public interface UserService {
    void addUser(final User user, final RegisterInfo registerInfo) throws IOException;

    User fetchUser(final MobileNumber mobileNumber);

    boolean isUserExists(final MobileNumber mobileNumber);

    UserInfo fetchUserInfo(final long ownerUid, final long userId) throws IOException;

    List<UserInfo> fetchUserInfoList(long ownerUid, List<Long> userIds);

    void addUserIntoBlacklist(final long ownerUid, final long userId) throws IOException;

    void addUserIntoBlacklist(final long ownerUid, final List<Long> userIdList);

    List<BlackItem> fetchUserBlacklist(final long ownerUid);

    void removeUserInBlacklist(final long ownerUid, final long userId);

    void removeUserInBlacklist(final long ownerUid, final List<Long> userIdList);

    void addContacts(final long ownerUid, final List<Contact> contacts);

    List<UserInfo> fetchAppContacts(final long ownerUid);

    String fetchChangedUserInfoData(final long ownerUid, final long beginSequenceId);
}
