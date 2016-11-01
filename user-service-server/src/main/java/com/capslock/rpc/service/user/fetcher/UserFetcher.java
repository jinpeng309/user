package com.capslock.rpc.service.user.fetcher;

import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.service.user.mapper.UserMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class UserFetcher {
    @Autowired
    private UserMapper userMapper;

    public User fetchUser(final long userId) {
        return userMapper.fetchUserById(userId);
    }

    public List<User> fetchUsersByUidList(final List<Long> uidList) {
        return userMapper.fetchUserByIds(uidList);
    }

    public Observable<List<User>> fetchUsersByUidListAsync(final List<Long> userIds) {
        return Observable.fromCallable(() -> fetchUsersByUidList(userIds));
    }

    public Observable<User> fetchUserAsync(final long userId) {
        return Observable.fromCallable(() -> fetchUser(userId));
    }

    public User fetchUser(final int countryCode, final long phoneNumber) {
        return userMapper.fetchUserByPhoneNumber(countryCode, phoneNumber);
    }

    public Observable<User> fetchUserAsync(final int countryCode, final long phoneNumber) {
        return Observable.fromCallable(() -> fetchUser(countryCode, phoneNumber));
    }

    public List<User> fetchUsers(final List<MobileNumber> mobileNumbers) {
        return Lists.newArrayList();
    }

    public Observable<List<User>> fetchUsersAsync(final List<MobileNumber> mobileNumbers) {
        return Observable.fromCallable(() -> fetchUsers(mobileNumbers));
    }

    public List<User> fetchUsersByEncryptedPhoneNumber(final List<String> encryptedPhoneNumbers) {
        return Lists.newArrayList();
    }

    public Observable<List<User>> fetchUsersByEncryptedPhoneNumberAsync(final List<String> encryptedPhoneNumbers) {
        return Observable.fromCallable(() -> fetchUsersByEncryptedPhoneNumber(encryptedPhoneNumbers));
    }
}
