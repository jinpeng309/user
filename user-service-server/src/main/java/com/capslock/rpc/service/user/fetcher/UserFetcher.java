package com.capslock.rpc.service.user.fetcher;

import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.service.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

/**
 * Created by alvin.
 */
@Component
public class UserFetcher {
    @Autowired
    private UserMapper userMapper;

    public User fetchUserById(final long userId) {
        return userMapper.fetchUserById(userId);
    }

    public Observable<User> fetchUserByIdAsync(final long userId) {
        return  Observable.fromCallable(() -> fetchUserById(userId));
    }

    public User fetchUserByPhoneNumber(final int countryCode, final long phoneNumber) {
        return userMapper.fetchUserByPhoneNumber(countryCode, phoneNumber);
    }

    public Observable<User> fetchUserByPhoneNumberAsync(final int countryCode, final long phoneNumber) {
        return  Observable.fromCallable(() -> fetchUserByPhoneNumber(countryCode, phoneNumber));
    }
}
